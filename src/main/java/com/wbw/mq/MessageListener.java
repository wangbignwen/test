package com.wbw.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    //@RabbitListener(queues = "wbw_queue")
    public void myListener1(String message) {
        System.out.println("消费者接收到的消息为：" + message);
    }

    @RabbitListener(queues = "wbw_queue")
    public void msg(Message message, Channel channel, String msg) {
        //接收消息
        System.out.println("消费者接收消息：" + msg);
        try {
            //处理本地业务
            System.out.println("处理本地业务开始======start======");
            Thread.sleep(1000);
            //int i = 1 / 0;
            System.out.println("处理本地业务结束======end======");
            //签收消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //e.printStackTrace();
            //如果出现异常，则拒绝消息 可以重回队列 也可以丢弃 可以根据业务场景来
            try {
                if (message.getMessageProperties().getRedelivered()) {
                    //消息已经重新投递，不需要再次投递
                    System.out.println("已经投递一次了，消息不会重新投递了");
                    //批量决绝
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
                } else {
                    //第三个参数：设置是否重回队列
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}