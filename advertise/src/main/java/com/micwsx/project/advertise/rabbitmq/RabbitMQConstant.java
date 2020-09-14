package com.micwsx.project.advertise.rabbitmq;

/**
 * @author Michael
 * @create 8/7/2020 10:36 AM
 */
public class RabbitMQConstant {
    // durable non-autodelete
    // 日志交换器，队列
    public final static String EXCHANGE_DIRECT_LOG="logs_exchange";
    public final static String QUEUE_LOG="logs_queue";
    public final static String BINDING_KEY="info";
    public final static String MESSAGE_INFO_ROUTING_KEY="info";
    public final static String MESSAGE_ERROR_ROUTING_KEY="error";


    // 备用交换器，队列
    public final static String EXCHANGE_FANOUT_ALTERNATE="alternate_exchange";
    public final static String QUEUE_ALTERNATE="alternate_queue";

    // 死信交换器，路由键，队列
    public final static String EXCHANGE_DEAD="dead_exchange";
    public final static String EXCHANGE_DEAD_ROUTINGKEY="dead";
    public final static String QUEUE_DEAD="dead_queue";







}
