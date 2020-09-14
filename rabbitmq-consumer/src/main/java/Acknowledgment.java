import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Michael
 * @create 8/5/2020 1:23 PM
 * 消息确认机制
 */
public class Acknowledgment {
    private static final String HOST = "192.168.1.111";
    private static final String USER = "michael";
    private static final String PASS = "michael";
    private static final String VHOST = "my_vhost";
    private static final String LOG_DIRECT = "logs_direct";
    private static String ALTERNATIVE_EXCHANGE_NAME="bak_exchange";

    public static void main(String[] args) {

        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(HOST);
            connectionFactory.setUsername(USER);
            connectionFactory.setPassword(PASS);
            connectionFactory.setVirtualHost(VHOST);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            Map<String, Object> arg = new HashMap<>();
            arg.put("alternate-exchange", ALTERNATIVE_EXCHANGE_NAME);
            // 主交换器
            channel.exchangeDeclare(LOG_DIRECT, BuiltinExchangeType.DIRECT, false, false, arg);
            // 备交换器
            channel.exchangeDeclare(ALTERNATIVE_EXCHANGE_NAME,  BuiltinExchangeType.FANOUT,true,false,null);

            String queueName = channel.queueDeclare().getQueue();
//            // 只处理error消息
//            channel.queueBind(queueName,LOG_DIRECT,"error");

            String[] rk = new String[]{"debug", "info", "waring"};
            for (String key : rk) {
                channel.queueBind(queueName,LOG_DIRECT,key);
            }

//            channel.basicConsume(queueName, false, (consumerTag, delivery) -> {
//                String routingKey = delivery.getEnvelope().getRoutingKey();
//                String exchange = delivery.getEnvelope().getExchange();
//                String message= new String(delivery.getBody(),"UTF-8");
//                Long deliveryTag= delivery.getEnvelope().getDeliveryTag();
//                StringBuffer stringBuffer=new StringBuffer();
//                stringBuffer.append("routingKey: "+routingKey).append("\r\n")
//                        .append("exchange: "+exchange).append("\r\n")
//                        .append("deliveryTag: "+deliveryTag).append("\r\n")
//                        .append("message: "+message);
//                System.out.println("收到消息:"+stringBuffer.toString());
//                channel.basicAck(deliveryTag,false);
//            }, consumerTag ->{} );

            channel.basicQos(5,false);
            channel.basicConsume(queueName, false, (consumerTag, delivery) -> {
                String routingKey = delivery.getEnvelope().getRoutingKey();
                String exchange = delivery.getEnvelope().getExchange();
                String message= new String(delivery.getBody(),"UTF-8");
                Long deliveryTag= delivery.getEnvelope().getDeliveryTag();
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer.append("routingKey: "+routingKey).append("\r\n")
                        .append("exchange: "+exchange).append("\r\n")
                        .append("deliveryTag: "+deliveryTag).append("\r\n")
                        .append("message: "+message);

                if (routingKey.equals("error")){
                    System.out.println("异常消息不被处理:"+stringBuffer.toString());
                    // 异常拒绝
                    channel.basicReject(deliveryTag, false);
                }else {
                    System.out.println("收到消息:"+stringBuffer.toString());
                    channel.basicAck(deliveryTag, false);
                }
            }, consumerTag ->{} );
            System.out.println("准备接收消息...");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }


}
