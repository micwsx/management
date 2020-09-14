import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael
 * @create 8/4/2020 11:24 AM
 */
public class SendWithDirect {

    private final static String HOST = "192.168.1.111";
    private final static String USER = "michael";
    private final static String PASS = "michael";
    private final static String VHOST = "my_vhost";
    private final static String EXCHANGE = "direct_logs";

    public static void main(String[] args) {

        try {
            ConnectionFactory connectionFactory=new ConnectionFactory();
            connectionFactory.setHost(HOST);
            connectionFactory.setUsername(USER);
            connectionFactory.setPassword(PASS);
            connectionFactory.setVirtualHost(VHOST);
            try(Connection connection = connectionFactory.newConnection()){
                Channel channel = connection.createChannel();
                channel.exchangeDeclare(EXCHANGE, "direct");
                // 启动发布者确认机制
                channel.confirmSelect();

                channel.addConfirmListener(new ConfirmListener() {
                    @Override
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println(" [x] Sent deliveryTag:'" + deliveryTag + "',multiple:"+multiple);
                    }

                    @Override
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println(" [x] unSent deliveryTag:'" + deliveryTag + "',multiple:"+multiple);
                    }
                });

                channel.addReturnListener(new ReturnListener() {
                    @Override
                    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        StringBuffer stringBuffer=new StringBuffer();
                        stringBuffer.append("replyCode:"+replyCode).append("r\n")
                                    .append("replyText:"+replyText).append("r\n")
                                    .append("exchange:"+exchange).append("r\n")
                                    .append("routingKey:"+routingKey).append("r\n")
                                    .append("properties:"+properties).append("r\n")
                                    .append("body:"+new String(body,"UTF-8"));
                        System.out.println(stringBuffer.toString());
                    }
                });

//                String severity="error";
//                String message="Something comes up!";
                for (int i = 1; i <=10 ; i++) {

                    String severity="info";
                    String message=String.valueOf(i);
//                    String message="Hello World["+String.valueOf(i)+"]";
                    channel.basicPublish(EXCHANGE, severity,true, null, message.getBytes());
//                    try {
//                        if (channel.waitForConfirms()){
//                            System.out.println(" [x] Sent '" + message + "'");
//                        }else{
//                            System.out.println("发送消息失败："+message);
//                        }
//                    } catch (InterruptedException e) {
//                        System.out.println("发送消息"+message+"异常："+e.getMessage());
//                    }
                    Thread.sleep(1_000);
                }




            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }



}
