package com.micwsx.project.advertise;

import com.micwsx.project.advertise.rabbitmq.RabbitMQConstant;
import com.micwsx.project.advertise.rabbitmq.TestMessage;
import com.micwsx.project.advertise.rabbitmq.producer.LogProducer;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author Michael
 * @create 8/7/2020 1:16 PM
 */
@SpringBootTest
@MapperScan("com.micwsx.project.advertise.dao")
public class RabbitmqTest {

    @Autowired
    private LogProducer logProducer;


    @Test
    public void sendObject() {
        for (int i = 1; i <= 20; i++) {
            TestMessage testMessage = new TestMessage(i, "[" + i + "]Michael", "SH[" + i + "]");
            if (i % 2 == 0) {
                // 发送
                logProducer.send(testMessage, 2000, RabbitMQConstant.MESSAGE_INFO_ROUTING_KEY);
            } else {
                // 模拟发送无法路由消息，让备用交换处理
                logProducer.send(testMessage, 2000, RabbitMQConstant.MESSAGE_ERROR_ROUTING_KEY);
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("发送结束");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void sendLog() {
        for (int i = 1; i <= 20; i++) {
            if (i % 2 == 0) {
                // 发送
                logProducer.send("[" + i + "]Hello!", 2000, RabbitMQConstant.MESSAGE_INFO_ROUTING_KEY);
            } else {
                // 模拟发送无法路由消息，让备用交换处理
                logProducer.send("[" + i + "]Hello!", 2000, RabbitMQConstant.MESSAGE_ERROR_ROUTING_KEY);
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("发送结束");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
