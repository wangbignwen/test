package com.wbw.service.userregisterservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements ApplicationListener<UserRegisterEvent> { // 实现监听接口
 
    private final Logger logger = LoggerFactory.getLogger(getClass());
 
    @Override
    @Async // 该方法异步执行
    public void onApplicationEvent(UserRegisterEvent event) { // 监听用户注册类
        logger.info("[onApplicationEvent][给用户({}) 发送邮件]", event.getUsername());
    }
 
}