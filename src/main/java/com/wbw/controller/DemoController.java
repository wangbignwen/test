package com.wbw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demo")
public class DemoController {
 
    @Resource
    private UserService userService;
 
    @GetMapping("/register")
    public String register(String username) {
        userService.register(username);
        return "success";
    }
 
}