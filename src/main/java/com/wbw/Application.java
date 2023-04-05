package com.wbw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync // 开启 Spring 异步的功能
@MapperScan("com.wbw.mapper")  //扫dao层，有该注解时dao层可以省略@Mapper注解
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("===============spring boot start==============");
    }
}




