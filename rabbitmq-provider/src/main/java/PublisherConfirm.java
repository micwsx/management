import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * @author Michael
 * @create 8/5/2020 10:26 AM
 * 生产者确认机制，同步单笔确认，批量确认。异常确认。
 */
public class PublisherConfirm {

    private final String HOST = "192.168.1.111";
    private final String USER = "michael";
    private final String PASS = "michael";
    private final String VHOST = "my_vhost";
    protected final String LOG_DIRECT = "logs_direct";
    protected final String ALTERNATIVE_EXCHANGE_NAME = "bak_exchange";

    protected Connection connection;
    protected Channel channel;

    /**
     *
     * @throws IOException
     * @throws TimeoutException
     */
    public PublisherConfirm() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setUsername(USER);
        connectionFactory.setPassword(PASS);
        connectionFactory.setVirtualHost(VHOST);
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        // 连接关闭时触发
        connection.addShutdownListener(cause -> {
            System.out.println("connection关闭：" + cause.getMessage());
        });
        // channel关闭时触发
        channel.addShutdownListener(cause -> {
            System.out.println("channel关闭：" + cause.getMessage());
        });

        // 失败通知机制
        channel.addReturnListener(new ReturnListenCallBack());

        // 异步发布者确认
        channel.addConfirmListener(new ConfirmCallBack());
    }

    public void close() {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException |TimeoutException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        try {
//            PublisherConfirm publisherConfirm = new PublisherConfirm();
//            publisherConfirm.publishConfirmation(publisherConfirm::singleConfirm);
//            publisherConfirm.publishConfirmation(publisherConfirm::batchConfirm);
//            publisherConfirm.publishConfirmation(publisherConfirm::asynchronizeConfirm);
//            publisherConfirm.alternativeExchange();
//            publisherConfirm.deadExchange();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 备用交换器。如果投放的消息没有路由队列，则进入备用交换器
     */
    public void alternativeExchange() {

        channel.addConfirmListener(new ConfirmCallBack());
        String ALTERNATIVE_EXCHANGE_NAME="bak_exchange";
        try {
            // 开户发送确认
            channel.confirmSelect();

            Map<String, Object> arg = new HashMap<>();
            arg.put("alternate-exchange", ALTERNATIVE_EXCHANGE_NAME);
            // 主交换器
            channel.exchangeDeclare(LOG_DIRECT, BuiltinExchangeType.DIRECT, false, false, arg);
            // 备交换器
            channel.exchangeDeclare(ALTERNATIVE_EXCHANGE_NAME,  BuiltinExchangeType.FANOUT,true,false,null);

            String[] routingKey = new String[]{"info", "error"};
            for (int i = 1; i <= 10; i++) {
                String message = String.valueOf(i);
                channel.basicPublish(LOG_DIRECT, routingKey[i % 2], null, message.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 死信交换器：
     *  1。队列已满
     *  2。消息被拒
     *  3。消息过期
     */
    public void deadExchange(){
        try {
            channel.addConfirmListener(new ConfirmCallBack());
            channel.confirmSelect();

            channel.exchangeDeclare(LOG_DIRECT, BuiltinExchangeType.DIRECT);
            String[] routingKey=new String[]{"info","debug"};
            for (int i = 1; i <=10 ; i++) {
                String message=String.valueOf(i);
                channel.basicPublish(LOG_DIRECT, routingKey[i%2],null,message.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送确认
     *
     * @param confirm
     */
    public void publishConfirmation(Consumer<Channel> confirm) {
        try {
            // 非持久化exchange,Rabbitmq重启后会删除。
            channel.exchangeDeclare(LOG_DIRECT, BuiltinExchangeType.DIRECT);
            // 开户发送确认
            channel.confirmSelect();
            // 确认
            confirm.accept(channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步批量确认
     */
    public void batchConfirm(Channel channel) {
        String[] routingKey = new String[]{"debug", "info", "waring", "error"};
        Random random = new Random();
        int batchSize = 25;
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            String message = String.valueOf(i);
            try {
                // 不开户mandatory失败通知 发送消息
                channel.basicPublish(LOG_DIRECT, routingKey[random.nextInt(4)], null, message.getBytes());
                count++;
                // 每发送完50条消息后确认一次
                if (count == batchSize) {
                    boolean b = channel.waitForConfirms(5_000);
                    if (b) {
                        System.out.println("成功发送消息数量：" + count);
                    } else {
                        System.out.println("失败发送消息：" + count);
                    }
                    count = 0;
                }
            } catch (IOException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        close();
    }

    /**
     * 同步单笔确认
     */
    public void singleConfirm(Channel channel) {
        String[] routingKey = new String[]{"debug", "info", "waring", "error"};
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            String message = String.valueOf(i);
            try {
                // 设置消息mandatory失败通知
                channel.basicPublish(LOG_DIRECT, routingKey[random.nextInt(4)], true, null, message.getBytes());
                // 表示等待已经发送给broker的消息act或者nack之后才会继续执行,发送确认超时时间5s
                boolean b = channel.waitForConfirms(5_000);
                // 表示等待已经发送给broker的消息act或者nack之后才会继续执行，如果有任何一个消息触发了nack则抛出IOException。
                // channel.waitForConfirmsOrDie();
                if (b) {
                    System.out.println("成功发送消息：" + message);
                } else {
                    System.out.println("失败发送消息：" + message);
                }
            } catch (IOException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        close();
    }

    /**
     * 异步单笔确认
     *
     * @param channel
     */
    public void asynchronizeConfirm(Channel channel) {

        channel.addConfirmListener(new ConfirmCallBack());
        String[] routingKey = new String[]{"debug", "info", "waring", "error"};
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            String message = String.valueOf(i);
            System.out.println(" [x] sent: '" + message + "'");
            try {
//                channel.basicPublish(LOG_DIRECT, routingKey[random.nextInt(4)],false, null, message.getBytes());
                channel.basicPublish(LOG_DIRECT, "error", false, null, message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReturnListenCallBack implements ReturnListener {
        @Override
        public void handleReturn(int replyCode,
                                 String replyText, String exchange,
                                 String routingKey, AMQP.BasicProperties properties,
                                 byte[] body) throws IOException {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("replyCode:" + replyCode).append("\r\n")
                    .append("replyText:" + replyText).append("\r\n")
                    .append("exchange:" + exchange).append("\r\n")
                    .append("routingKey:" + routingKey).append("\r\n")
                    .append("properties:" + properties).append("\r\n")
                    .append("body:" + new String(body, "UTF-8"));
            System.out.println(stringBuffer.toString());
        }
    }

    private class ConfirmCallBack implements ConfirmListener {
        @Override
        public void handleAck(long deliveryTag, boolean multiple) throws IOException {
            System.out.println("成功发送消息：deliveryTag:" + deliveryTag + " multiple:" + multiple);
        }

        @Override
        public void handleNack(long deliveryTag, boolean multiple) throws IOException {
            System.out.println("失败发送消息：deliveryTag:" + deliveryTag + " multiple:" + multiple);
        }
    }
}
