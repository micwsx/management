import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/3/2020 1:41 PM
 * 生产者向hello队列中发送消息。模拟耗时任务，多worker一起消费同个工作队列中的消息。
 */
public class SendToWorkQueue {

    private final static String QUEUE_NAME="hello";
    private final static String HOST="192.168.1.111";
    private final static String USER="michael";
    private final static String PASS="michael";
    private final static String VHOST="my_vhost";


    public static void main(String[] args) {
        try {
            ConnectionFactory factory=new ConnectionFactory();
            factory.setHost(HOST);
            factory.setUsername(USER);
            factory.setPassword(PASS);
            factory.setVirtualHost(VHOST);
            try(Connection connection=factory.newConnection()){
                Channel channel=connection.createChannel();
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                String message=String.join(" ",args);
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
