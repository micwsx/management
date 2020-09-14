import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/6/2020 12:35 PM
 * 设置属性消息
 */
public class ProduceExpiredMessage extends PublisherConfirm {

    private final String queueName = "logs_queue";
    // 备用服务器
    private final String ALTERNATIVE_EXCHANGE_NAME = "bak_exchange";
    // 死信队列
    private final String DEAD_EXCHANGE_NAME = "dead_exchange";
    private final String DEAD_ROUTINGKEY_NAME = "dead";

    public ProduceExpiredMessage() {
    }


    private void sendMessage(String... routingKey) {
        try {
            // 开启发布者确认
            channel.confirmSelect();
            // 备用交换器
            Map<String, Object> arg = new HashMap<>();
            arg.put("alternate-exchange", ALTERNATIVE_EXCHANGE_NAME);
            // 声名主交换器
            channel.exchangeDeclare(LOG_DIRECT, BuiltinExchangeType.DIRECT, true, false, arg);
            // 声名备用交换器
            channel.exchangeDeclare(ALTERNATIVE_EXCHANGE_NAME, BuiltinExchangeType.FANOUT, true, false, null);
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
            // 绑定队列和direct
            for (String s : routingKey) {
                channel.queueBind(queueName, LOG_DIRECT, s);
            }
            String replytoQueueName = channel.queueDeclare().getQueue();
            Random random = new Random();
            for (int i = 1; i <= 20; i++){
                String message = String.valueOf(i);
                System.out.println("[x] sent: " + message);
                int index = routingKey.length == 1 ? 0 : random.nextInt(routingKey.length);
                // 设置消息过期时间属性
                AMQP.BasicProperties basicProperties=new AMQP.BasicProperties.Builder()
                        .replyTo(replytoQueueName)
                        .messageId(UUID.randomUUID().toString())
                        .deliveryMode(2)
                        .expiration(String.valueOf(10)).build();
                channel.basicPublish(LOG_DIRECT, routingKey[index], true, basicProperties, message.getBytes());
                // 处理回复
                channel.basicConsume(basicProperties.getReplyTo(), true, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.out.println("收到：routingKey("+envelope.getRoutingKey()+")"+new String(body,"UTF-8"));
                    }
                });


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ProduceExpiredMessage produce = new ProduceExpiredMessage();
//        produce.sendMessage("error", "info", "waring");
        // 发送20条消息
        produce.sendMessage("info");

    }
}
