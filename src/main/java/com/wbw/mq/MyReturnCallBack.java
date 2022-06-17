package com.wbw.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyReturnCallBack implements RabbitTemplate.ReturnCallback {
    /**
     * @param message    消息信息
     * @param replyCode  退回的状态码
     * @param replyText  退回的信息
     * @param exchange   交换机
     * @param routingKey 路由key
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("退回的消息是：" + new String(message.getBody()));
        System.out.println("退回的replyCode是：" + replyCode);
        System.out.println("退回的replyText是：" + replyText);
        System.out.println("退回的exchange是：" + exchange);
        System.out.println("退回的routingKey是：" + routingKey);
    }
}
