import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/4/2020 11:24 AM
 */
public class ReceiveWithDirect {

    private final static String HOST = "192.168.1.111";
    private final static String USER = "michael";
    private final static String PASS = "michael";
    private final static String VHOST = "my_vhost";
    private final static String EXCHANGE = "direct_logs";

    public static void main(String[] args) {

        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(HOST);
            connectionFactory.setUsername(USER);
            connectionFactory.setPassword(PASS);
            connectionFactory.setVirtualHost(VHOST);

            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, "direct");

            String queueName = channel.queueDeclare().getQueue();
            for (String severity : args) {
                // 一个队列绑定多个bindkey,接收多种类型消息
                channel.queueBind(queueName, EXCHANGE, severity);
            }

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicQos(5);

            channel.basicConsume(queueName, false, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                String routingKey = delivery.getEnvelope().getRoutingKey();
                if (Integer.parseInt(message) == 3) {
                    try {
                        throw new RuntimeException("Something comes up!");
                    }catch (Exception ex){
                        System.out.println(consumerTag + "未收到消息： " + message);
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                    }
                }else{
                    // 确认收到
                    System.out.println(consumerTag+" [x] Recieve " + routingKey + " :'" + message + "'");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }

//                if (Integer.parseInt(message)<=3){
//                    // 否认收到
//                    System.out.println(consumerTag+"未收到消息： "+message);
//                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false,true);
//                }else {
//                    // 确认收到
//                    System.out.println(consumerTag+" [x] Recieve " + routingKey + " :'" + message + "'");
//                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, consumerTag -> {
                System.out.println("consumerTag: " + consumerTag + " has cancelled!");
            });


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }


}
