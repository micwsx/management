import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/3/2020 1:51 PM
 * 消费者接收hello队列中Hello World消息。
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";
    private final static String HOST = "192.168.1.111";
    private final static String USER="michael";
    private final static String PASS="michael";
    private final static String VHOST="my_vhost";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setVirtualHost(VHOST);
            factory.setUsername(USER);
            factory.setPassword(PASS);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback=(consumerTag,delivery)->{
                String message=new String(delivery.getBody(),"UTF-8");
                System.out.println(" [x] Received: '"+message+"'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
