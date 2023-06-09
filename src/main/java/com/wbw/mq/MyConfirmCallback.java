package com.wbw.mq;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {
    /**
     * @param correlationData 消息信息
     * @param ack             确认标识：true,MQ服务器exchange表示已经确认收到消息 false 表示没有收到消息
     * @param cause           如果没有收到消息，则指定为MQ服务器exchange消息没有收到的原因，如果已经收到则指定为null
     */
    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
        if (ack) {
            System.out.println("发送消息到交换机成功," + cause);
        } else {
            System.out.println("发送消息到交换机失败,原因是：" + cause);
        }
    }
}
