package com.wbw.service.userregisterservice;

import com.wbw.controller.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CouponService {
 
    private final Logger logger = LoggerFactory.getLogger(getClass());
 
    @EventListener // 监听事件
    public void addCoupon(UserRegisterEvent event) {
        logger.info("[addCoupon][给用户({}) 发放优惠劵]", event.getUsername());
    }
}