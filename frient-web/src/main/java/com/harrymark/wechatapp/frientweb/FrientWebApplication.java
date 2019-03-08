package com.harrymark.wechatapp.frientweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.harrymark.wechatapp"})
@MapperScan("com.harrymark.wechatapp.frientdao.dao")
public class FrientWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrientWebApplication.class, args);
    }
}
