package com.wbw;

import com.wbw.mq.MyConfirmCallback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {
    //用于发送MQ消息
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitTemplate.ConfirmCallback myConfirmCallback;

    @Autowired
    private RabbitTemplate.ReturnCallback myReturnCallback;

    @Test
    public void testCreateMessage() {
        //Mandatory为true时,消息通过交换器无法匹配到队列会返回给生产者 并触发MessageReturn
        //为false时,匹配不到会直接被丢弃 默认为true
        //rabbitTemplate.setMandatory(true);

        //设置回调函数 product 到 exchange
        rabbitTemplate.setConfirmCallback(myConfirmCallback);

        //设置回调函数 exchange 到 queue
        rabbitTemplate.setReturnCallback(myReturnCallback);

        // 发送成功
        //rabbitTemplate.convertAndSend("wbw_exchange", "wbw.delete", "王炳文添加消息");
        //rabbitTemplate.convertAndSend("wbw_exchange", "wbw.add", "王炳文添加消息");

        // product 到 exchange错误
        //rabbitTemplate.convertAndSend("wbww_exchange", "wbw.add", "王炳文添加错误的交换机消息");

        // exchange 到 queue错误
        //rabbitTemplate.convertAndSend("wbw_exchange", "wbww.add", "王炳文添加错误的路由key消息");
    }


}
