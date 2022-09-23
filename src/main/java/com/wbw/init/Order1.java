package com.wbw.init;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class Order1 extends Orders {
    public Order1(){
        System.out.println("order1初始化完成");
    }

    @Override
    public void doJob() {
        System.out.println("按顺序做第二类事");
    }
}
