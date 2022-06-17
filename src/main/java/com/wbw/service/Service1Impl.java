package com.wbw.service;

import com.wbw.mapper.Mapper1;
import com.wbw.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class Service1Impl implements Service1 {
    @Resource
    private Mapper1 mapper1;

    @Override
    public String addUser(String name) {
        User user = new User();
        user.setId(1001);
        user.setName("王炳文");
        mapper1.add(user);
        return "success";
    }
}
