import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/3/2020 1:41 PM
 * 生产者fanout广播消息
 */
public class SendWithExchange {

    private final static String HOST = "192.168.1.111";
    private final static String USER = "michael";
    private final static String PASS = "michael";
    private final static String VHOST = "my_vhost";
    private final static String EXCHANGE = "logs";


    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setUsername(USER);
            factory.setPassword(PASS);
            factory.setVirtualHost(VHOST);

            try (Connection connection = factory.newConnection()) {
                Channel channel = connection.createChannel();
                // 生产者声名一个fanout类型exchange
                channel.exchangeDeclare("EXCHANGE", "fanout");
                String message = "Hello World2!";
                // 使用声名的exchange名称,将消息发送到exchange中。
                channel.basicPublish(EXCHANGE, "", null, message.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
