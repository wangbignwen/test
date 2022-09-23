package com.wbw.init;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Order2 extends Orders {
    public Order2(){
        System.out.println("order2初始化完成");
    }

    @Override
    public void doJob() {
        System.out.println("按顺序做第一类事");
    }
}
