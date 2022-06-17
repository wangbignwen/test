package com.wbw.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //Fanout：广播，将消息交给所有绑定到交换机的队列
    //Direct：定向，把消息交给符合指定routing key 的队列
    //Topic：通配符，把消息交给符合routing pattern（路由模式） 的队列
    @Bean
    public Queue queue() {
        return new Queue("wbw_queue");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("wbw_exchange");
    }

    //通配符模式的规则
    //item.#：能够匹配item.insert.abc 或者 item.insert
    //item.*：只能匹配item.insert
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("wbw.*");
    }


    // 创建过期队列，队列中的所有消息在过期时间到之后，如果没有被消费则被全部清除
    // 设置参数：x-message-ttl为10秒后过期
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder.durable("wbw_ttlQueue").withArgument("x-message-ttl", 10000).build();
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(ttlQueue()).to(exchange()).with("ttl.*");
    }


    // 创建死信队列
    // 用来接收生产者发送过来的消息 然后要过期 变成死信 转发给了queue
    @Bean
    public Queue DLXQueue(){
        return QueueBuilder
                .durable("dlx_queue")
                .withArgument("x-max-length",1)//设置队列的长度
                .withArgument("x-message-ttl",10000)//设置队列的消息过期时间 10S
                .withArgument("x-dead-letter-exchange","dlx_exchange")//设置死信交换机名称
                .withArgument("x-dead-letter-routing-key","dlx.*")//设置死信路由key
                .build();
    }

    // 创建死信交换机 Dead Letter Exchange DLX
    @Bean
    public DirectExchange DLXExchange(){
        return new DirectExchange("dlx_exchange");
    }

    // queue 绑定给 死信交换机, 死信交换机中有消息时转发给queue
    // routingkey 和队列转发消息时指定的死信routingkey 要一致
    @Bean
    public Binding binding3(){
        return BindingBuilder.bind(queue()).to(DLXExchange()).with("dlx.*");
    }
}
