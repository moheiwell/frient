package com.harrymark.wechatapp.frientweb.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by haoweima on 2019/3/14.
 */

@Configuration
public class GsonFactoryConfiguration {

    @Bean
    public Gson gsonFactory(){
        return new Gson();
    }
}
