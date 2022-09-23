package com.wbw.init;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Test {

    List<Orders> ordersList;

    public Test(Test2 test2, List<Orders> orders) {
        test2.test();
        this.ordersList=orders;
        System.out.println("test初始化完成");
    }
}
