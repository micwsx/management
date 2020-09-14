import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/5/2020 1:23 PM
 * 消息确认机制
 */
public class FocusError {
    private static final String HOST = "192.168.1.111";
    private static final String USER = "michael";
    private static final String PASS = "michael";
    private static final String VHOST = "my_vhost";
    private static final String LOG_DIRECT = "logs_direct";

    public static void main(String[] args) {

        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(HOST);
            connectionFactory.setUsername(USER);
            connectionFactory.setPassword(PASS);
            connectionFactory.setVirtualHost(VHOST);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(LOG_DIRECT, BuiltinExchangeType.DIRECT);

            String queueName = "main_queu";
            channel.queueDeclare(queueName, false, true,true,null );
            // 只处理error消息
            channel.queueBind(queueName, LOG_DIRECT, "error");

            channel.basicQos(5, false);
            channel.basicConsume(queueName, false, (consumerTag, delivery) -> {
                String routingKey = delivery.getEnvelope().getRoutingKey();
                String exchange = delivery.getEnvelope().getExchange();
                String message = new String(delivery.getBody(), "UTF-8");
                Long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("routingKey: " + routingKey).append("\r\n")
                        .append("exchange: " + exchange).append("\r\n")
                        .append("deliveryTag: " + deliveryTag).append("\r\n")
                        .append("message: " + message);

                if (message.equals("2")||message.equals("3")){
                    System.out.println("消费者【1】拒绝处理异常消息:"+message);
//                    channel.basicReject(deliveryTag, true);
                    channel.basicNack(deliveryTag, false, true);
                }else {
                    System.out.println("消费者【1】处理异常消息:" + stringBuffer.toString());
                    channel.basicAck(deliveryTag, false);
                }
            }, consumerTag -> {
            });

            channel.basicConsume(queueName, false, (consumerTag, delivery) -> {
                String routingKey = delivery.getEnvelope().getRoutingKey();
                String exchange = delivery.getEnvelope().getExchange();
                String message = new String(delivery.getBody(), "UTF-8");
                Long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("routingKey: " + routingKey).append("\r\n")
                        .append("exchange: " + exchange).append("\r\n")
                        .append("deliveryTag: " + deliveryTag).append("\r\n")
                        .append("message: " + message);
                System.out.println("消费者【2】处理异常消息:" + stringBuffer.toString());
                channel.basicAck(deliveryTag, false);
            }, consumerTag -> {
            });

            System.out.println("准备接收异常消息...");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }


}
