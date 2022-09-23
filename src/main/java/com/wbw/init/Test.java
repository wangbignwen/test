package com.wbw.init;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Test {

    List<Job> jobList;

    public Test(Test2 test2, List<Job> jobList) {
        test2.test();
        this.jobList=jobList;
        this.test();
        System.out.println("test初始化完成");
    }

    private void test() {
        jobList.get(0).doJob();
    }
}
