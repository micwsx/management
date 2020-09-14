package com.micwsx.project.advertise.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @create 8/7/2020 11:27 AM
 * 日志消息者
 */
@Component
public class LogConsumer implements ChannelAwareMessageListener {

    private int i = 0;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            channel.basicQos(5, false);
            i++;
            // 消息者确认
            String content = new String(message.getBody(), "UTF-8");
            System.out.println("[消费>>>>>>>>>>>>>>] receive: " + content+", consumerTag="+message.getMessageProperties().getConsumerTag());
            System.out.println("i="+i);
            if (i == 5){
                System.out.println("拒绝消息");
                // 死信队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            // 等待3秒
            Thread.sleep(3_000);
        } catch (Exception ex) {
            // 死信队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
