package com.micwsx.project.advertise.rabbitmq;

import com.micwsx.project.advertise.rabbitmq.consumer.AlternateConsumer;
import com.micwsx.project.advertise.rabbitmq.consumer.DeadConsumer;
import com.micwsx.project.advertise.rabbitmq.consumer.LogConsumer;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Consumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael
 * @create 8/6/2020 5:39 PM
 */
@Configuration
public class RabbitmqConfig {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost(rabbitProperties.getHost());
        cf.setUsername(rabbitProperties.getUsername());
        cf.setPassword(rabbitProperties.getPassword());
        cf.setVirtualHost(rabbitProperties.getVirtualHost());
        cf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.valueOf(rabbitProperties.getPublisherConfirmType()));
        cf.setPublisherReturns(rabbitProperties.isPublisherReturns());
        return cf;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setConfirmCallback(callback());
        template.setReturnCallback(returnCallback());
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    // 添加Direct附加备用交换器
    @Bean
    public DirectExchange exchangeDirectWithAlternate(){

        Map<String,Object> alternateExchangeMap=new HashMap<>();
        alternateExchangeMap.put("alternate-exchange", RabbitMQConstant.EXCHANGE_FANOUT_ALTERNATE);
        DirectExchange directExchange=new DirectExchange(RabbitMQConstant.EXCHANGE_DIRECT_LOG,true,
                false,alternateExchangeMap);
        return  directExchange;
    }

    // 备用交换器
    @Bean
    public FanoutExchange exchangeFanoutAlternate(){
        FanoutExchange fanoutExchange=new FanoutExchange(RabbitMQConstant.EXCHANGE_FANOUT_ALTERNATE, true, false);
        return  fanoutExchange;
    }

    // 死信交换器
    @Bean
    public Exchange exchangeDirectDead(){
        DirectExchange directExchange=new DirectExchange(RabbitMQConstant.EXCHANGE_DEAD,true,false);
        return  directExchange;
    }

    // 绑定主交换器与主队列
    @Bean
    public Binding bindingLogs(Queue logsQueue,DirectExchange exchangeDirectWithAlternate){
        // 绑定queue和exchange及匹配消息的routingKey与bindingkey
        return  BindingBuilder.bind(logsQueue).to(exchangeDirectWithAlternate).with(RabbitMQConstant.BINDING_KEY);
    }

    // 绑定备用交换器与队列
    @Bean
    public Binding bindingAlternate(Queue alternateQueue,FanoutExchange exchangeFanoutAlternate){
        // 绑定queue和exchange及匹配消息的routingKey与bindingkey
        return  BindingBuilder.bind(alternateQueue).to(exchangeFanoutAlternate);
    }

    // 绑定死信交换器与队列
    @Bean
    public Binding bindingDead(Queue deadQueue,DirectExchange exchangeDirectDead){
        // 绑定queue和exchange及匹配消息的routingKey与bindingkey
        return  BindingBuilder.bind(deadQueue).to(exchangeDirectDead).with(RabbitMQConstant.EXCHANGE_DEAD_ROUTINGKEY);
    }

    // 主队列添加死信队列
    @Bean
    public Queue logsQueue(){
        Map<String,Object> map=new HashMap<>();
        map.put("x-max-length", 10);
        map.put("x-dead-letter-exchange",RabbitMQConstant.EXCHANGE_DEAD);
        map.put("x-dead-letter-routing-key",RabbitMQConstant.EXCHANGE_DEAD_ROUTINGKEY);
        return  new Queue(RabbitMQConstant.QUEUE_LOG,true,false,false,map);
    }

    // 备用队列
    @Bean
    public Queue alternateQueue(){
        return  new Queue(RabbitMQConstant.QUEUE_ALTERNATE,true,false,false);
    }

    // 死信队列
    @Bean
    public Queue deadQueue(){
        return  new Queue(RabbitMQConstant.QUEUE_DEAD,true,false,false);
    }

    @Autowired
    private LogConsumer logConsumer;

    // 消费者
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(logsQueue());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setupMessageListener(logConsumer);
        // 默认采用下面的这种转换器
        // container.setMessageConverter(new SimpleMessageConverter());
        // 设置2个消息者
//        container.setConcurrentConsumers(2);
        return container;
    }

    @Autowired
    private DeadConsumer deadConsumer;

    @Bean
    public SimpleMessageListenerContainer deadMessageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(deadQueue());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setupMessageListener(deadConsumer);
        return container;
    }

    @Autowired
    private AlternateConsumer alternateConsumer;

    @Bean
    public SimpleMessageListenerContainer alternateMessageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(alternateQueue());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setupMessageListener(alternateConsumer);
        return container;
    }



    //生产者消息确认publisher confirmation回调方法
    private RabbitTemplate.ConfirmCallback callback() {
        return new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack){
                    System.out.println("发送者确认发送给mq成功");
                }else {
                    //处理失败的消息
                    System.out.println("发送者发送给mq失败,考虑重发:"+cause);
                }
            }
        };
    }

    //失败通知mandatory回调方法
    private RabbitTemplate.ReturnCallback returnCallback() {

        return new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer.append("无法路由的消息，需要考虑另外处理。")
                    .append("Returned replyText："+replyText)
                    .append(" Returned exchange："+exchange)
                    .append(" Returned routingKey："+routingKey)
                    .append(" Body:"+ new String(message.getBody()));
                System.out.println("Returned Message："+stringBuffer.toString());
            }
        };
    }


}
