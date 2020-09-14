package com.micwsx.project.advertise.rabbitmq.producer;

import com.micwsx.project.advertise.rabbitmq.RabbitMQConstant;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.postprocessor.MessagePostProcessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @create 8/7/2020 12:41 PM
 */
@Component
public class LogProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *发送消息
     * @param body：消息内容
     * @param rountingkey：消息路径键
     */
    public void send(String body, String rountingkey) {
        System.out.println("[LogProducer] sent: " + body);
        // 向日志交换器发送消息(消息路由键是info)
        this.rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_DIRECT_LOG, rountingkey, body);
    }

    /**
     * 发送消息（消息属性）
     * @param body：消息内容
     * @param properties:消息属性
     * @param rountingkey：消息路径键
     */
    public void send(String body, MessageProperties properties, String rountingkey) {
        Message message=new Message(body.getBytes(),properties);
        System.out.println("[LogProducer] sent: " + body);
        // 向日志交换器发送消息(消息路由键是info)
        this.rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_DIRECT_LOG, rountingkey, message);
    }

    /**
     * 发送持久化消息，有效期
     * @param body
     * @param expiration
     * @param rountingkey
     */
    public void send(String body, int expiration, String rountingkey) {
        MessageProperties properties=new MessageProperties();
        properties.setExpiration(String.valueOf(expiration));
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message=new Message(body.getBytes(),properties);
        System.out.println("[LogProducer] sent: " + body);
        // 向日志交换器发送消息(消息路由键是info)
        this.rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_DIRECT_LOG, rountingkey, message);
    }


    /**
     * 发送对象消息
     * @param body
     * @param expiration
     * @param rountingkey
     */
    public void send(Object body, int expiration, String rountingkey) {
        System.out.println("[LogProducer] sent: " + body);
        // 向日志交换器发送消息(消息路由键是info)
        this.rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_DIRECT_LOG, rountingkey, body, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setExpiration(String.valueOf(expiration));
                messageProperties.setHeader("desc", "michael");
                messageProperties.setContentType("application/json");
                return message;
            }
        });
    }

}
