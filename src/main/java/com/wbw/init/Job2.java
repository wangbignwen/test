package com.wbw.init;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Job2 extends Job {
    public Job2(){
        System.out.println("Job2初始化完成");
    }

    @Override
    public void doJob() {
        System.out.println("Job2中的doJob");
    }
}
