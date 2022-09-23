package com.wbw.init;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class Job1 extends Job {
    public Job1(){
        System.out.println("Job1初始化完成");
    }

    @Override
    public void doJob() {
        System.out.println("Job1中的doJob");
    }
}
