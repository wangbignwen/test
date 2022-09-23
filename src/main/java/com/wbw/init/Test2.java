package com.wbw.init;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class Test2 {
    @Resource
    List<Job> jobList;

    @Resource
    Map<String, Job> jobMap;

    public void test() {
        jobList.forEach(Job::doJob);
        jobMap.keySet().forEach(o->{
            if (o.contains("order1")){
                jobMap.get(o).doJob();
            }
        });
    }


}
