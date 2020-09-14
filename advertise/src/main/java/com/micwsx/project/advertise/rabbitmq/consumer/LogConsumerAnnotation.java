package com.micwsx.project.advertise.rabbitmq.consumer;

import com.micwsx.project.advertise.rabbitmq.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @create 8/7/2020 1:05 PM
 * 注解的方式消费消息
 */
//@Component
//@RabbitListener(queues = RabbitMQConstant.QUEUE_LOG)
public class LogConsumerAnnotation {

//    @RabbitHandler
    public void process(String message){
        System.out.println("[LogConsumerAnnotation] received: "+message);
    }
}
