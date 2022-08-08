package com.wbw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements ApplicationListener<UserRegisterEvent> { // <1>
 
    private final Logger logger = LoggerFactory.getLogger(getClass());
 
    @Override
    @Async // <3>
    public void onApplicationEvent(UserRegisterEvent event) { // <2>
        logger.info("[onApplicationEvent][给用户({}) 发送邮件]", event.getUsername());
    }
 
}