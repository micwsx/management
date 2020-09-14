package com.micwsx.project.advertise.rabbitmq.consumer;

import com.micwsx.project.advertise.rabbitmq.RabbitMQConstant;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @create 8/7/2020 12:36 PM
 */
@Component
public class AlternateConsumer implements ChannelAwareMessageListener{

//    @RabbitListener(bindings = @QueueBinding(value = @Queue(value =  RabbitMQConstant.QUEUE_ALTERNATE,durable = "true"),
//            exchange = @Exchange(value = RabbitMQConstant.EXCHANGE_FANOUT_ALTERNATE,type = "fanout",durable = "true",ignoreDeclarationExceptions = "false"),
//            key = "#"))
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String body = new String(message.getBody(), "UTF-8");
            System.out.println("[备用**********] receives: " + body);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception ex) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            throw ex;
        }
    }
}
