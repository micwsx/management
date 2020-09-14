package com.micwsx.project.advertise.rabbitmq.consumer;

import com.micwsx.project.advertise.rabbitmq.RabbitMQConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Michael
 * @create 8/7/2020 12:23 PM
 * 死信消息者
 */
@Component
public class DeadConsumer implements ChannelAwareMessageListener {

//    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = RabbitMQConstant.QUEUE_DEAD, durable = "true",autoDelete = "false"),
//            exchange = @Exchange(value = RabbitMQConstant.EXCHANGE_DEAD, type = "direct", durable = "true", ignoreDeclarationExceptions = "false"),
//            key = RabbitMQConstant.EXCHANGE_DEAD_ROUTINGKEY))
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String content = new String(message.getBody(), "UTF-8");
            System.out.println("[死信~~~~~~~~~~~~~~~] receives: " + content + " routingKey: " + message.getMessageProperties().getReceivedRoutingKey());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception ex) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
