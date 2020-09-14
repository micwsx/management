import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/3/2020 1:41 PM
 * 生产者向hello队列中发送Hello World.
 */
public class Send {

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
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message="Hello World!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
