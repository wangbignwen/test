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
        jobList.get(0).doJob();
        jobMap.keySet().forEach(i->{
            if (i.contains("job1")){
                jobMap.get(i).doJob();
            }
        });
    }


}
