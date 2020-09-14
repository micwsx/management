import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/6/2020 12:56 PM
 */
public class Receiver {
    private final String HOST = "192.168.1.111";
    private final String USER = "michael";
    private final String PASS = "michael";
    private final String VHOST = "my_vhost";
    private final String LOG_DIRECT = "logs_direct";
    private final String queueName = "logs_queue";
    // 备用服务器
    private final String ALTERNATIVE_EXCHANGE_NAME = "bak_exchange";
    // 死信队列
    private final String DEAD_EXCHANGE_NAME = "dead_exchange";
    private final String DEAD_ROUTINGKEY_NAME = "dead";

    private Connection connection;
    private Channel channel;

    public Receiver() {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setVirtualHost(VHOST);
        connectionFactory.setUsername(USER);
        connectionFactory.setPassword(PASS);

        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理 info,waring消息
     *
     * @param consumer
     */
    public void process(ConsumerInfo consumer) {
        try {
            Map<String, Object> arg = new HashMap<>();
            arg.put("alternate-exchange", ALTERNATIVE_EXCHANGE_NAME);
            // 设置备用交换器
            channel.exchangeDeclare(LOG_DIRECT, BuiltinExchangeType.DIRECT, true, false, arg);
            Map<String, Object> queueMap = new HashMap<>();
            queueMap.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
            queueMap.put("x-dead-letter-routing-key", DEAD_ROUTINGKEY_NAME);
            // 设置队列长度
            queueMap.put("x-max-length", 10);//10个
            // 设置队列有效时间
//            queueMap.put("x-expires", 10_000);//2秒
            // 设置队列消息有效时间
//            queueMap.put("x-message-ttl", 5_000);//100秒

            // 声名队列并设置死信队列
            channel.queueDeclare(queueName, true, false, false, queueMap);
            String[] routingKey = new String[]{"info", "waring"};
            for (String k : routingKey) {
                // 队列绑定exchange,只接收routingKey是info和waring的消息
                channel.queueBind(queueName, LOG_DIRECT, k);//bindingKey和routingKey
            }
            System.out.println(consumer.getName() + "等待处理消息。。。");
            channel.basicConsume(queueName, false, consumer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理死信
     */
    public void processDead() {
        try {
            channel.exchangeDeclare(DEAD_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, DEAD_EXCHANGE_NAME, DEAD_ROUTINGKEY_NAME);

            System.out.println("等待接收死信消息。。。");
            channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String exchange = envelope.getExchange();
                    String message = new String(body, "UTF-8");
                    Long deliveryTag = envelope.getDeliveryTag();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("routingKey: " + routingKey + ",")
                            .append("exchange: " + exchange).append(",")
                            .append("deliveryTag: " + deliveryTag).append(",")
                            .append("message: " + message).append("\r\n");
                    System.out.println("死信消息者[" + getConsumerTag() + "] received: " + stringBuffer.toString());

                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processBak() {
        try {
            channel.exchangeDeclare(ALTERNATIVE_EXCHANGE_NAME, BuiltinExchangeType.FANOUT, true, false, null);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, ALTERNATIVE_EXCHANGE_NAME, "#");
            channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String exchange = envelope.getExchange();
                    String message = new String(body, "UTF-8");
                    Long deliveryTag = envelope.getDeliveryTag();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("routingKey: " + routingKey + ",")
                            .append("exchange: " + exchange).append(",")
                            .append("deliveryTag: " + deliveryTag).append(",")
                            .append("message: " + message).append("\r\n");
                    System.out.println("备用交换器消息者[" + getConsumerTag() + "] received: " + stringBuffer.toString());

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void close() {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        receiver.process(new ConsumerInfo(receiver.getChannel(), "Jack"));
//        receiver.process(new ConsumerInfo(receiver.getChannel(), "Michael"));
        receiver.processDead();
//        receiver.processBak();
    }
}
