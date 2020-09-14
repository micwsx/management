import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/3/2020 1:51 PM
 * 消息处理手动确认
 */
public class ReceiveWithExchange {
    private final static String HOST = "192.168.1.111";
    private final static String USER="michael";
    private final static String PASS="michael";
    private final static String VHOST="my_vhost";
    private final static String EXCHANGE="logs";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setVirtualHost(VHOST);
            factory.setUsername(USER);
            factory.setPassword(PASS);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // 消费者声名exchange
            channel.exchangeDeclare(EXCHANGE,"fanout");
            // 创建none-durable,auto-delete,exclusive随机队列
            String queueName = channel.queueDeclare().getQueue();
            // 绑定exchange和对应消费队列(指定fanout消息发送到指定队列)
            channel.queueBind(queueName, EXCHANGE, "");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicQos(1);// accept only one unack-ed message at a time.(一次只接收一条消息)
            DeliverCallback deliverCallback=(consumerTag,delivery)-> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received: '" + message + "'");
                System.out.println(" [x] Done");
                // 手动确认消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            // 消费队列消息
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
