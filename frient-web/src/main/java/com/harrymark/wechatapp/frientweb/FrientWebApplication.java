package com.harrymark.wechatapp.frientweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.harrymark.wechatapp"})
public class FrientWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrientWebApplication.class, args);
    }

}
