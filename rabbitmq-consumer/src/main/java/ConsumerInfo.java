import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author Michael
 * @create 8/5/2020 5:58 PM
 * 只处理info消息
 */
public class ConsumerInfo extends DefaultConsumer {


    private String name = "Unknow_Consumer";

    public String getName() {
        return name;
    }

    public ConsumerInfo(Channel channel, String name) {
        super(channel);
        this.name = name;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String routingKey = envelope.getRoutingKey();
        String exchange = envelope.getExchange();
        String message = new String(body, "UTF-8");
        Long deliveryTag = envelope.getDeliveryTag();
        if (routingKey.equals("info")){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("routingKey: " + routingKey + ",")
                    .append("exchange: " + exchange).append(",")
                    .append("expiration: " + properties.getExpiration()).append(",")
                    .append("deliveryTag: " + deliveryTag).append(",")
                    .append("message: " + message).append("\r\n");
            System.out.println("[" + this.name + "][" + this.getConsumerTag() + "] received: " + stringBuffer.toString());
            // 消息确认
            this.getChannel().basicAck(deliveryTag, false);

            // 回复
            AMQP.BasicProperties prop=new AMQP.BasicProperties().builder()
                    .replyTo(properties.getReplyTo())
                    .messageId(properties.getMessageId()).build();
            this.getChannel().basicPublish("", properties.getReplyTo(), prop,("["+this.name+"]收到消息："+message).getBytes());
        }else{
            // 拒绝消息，放入死信队列中。
            getChannel().basicReject(deliveryTag, false);
        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
