package com.harrymark.wechatapp.frientweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by haoweima on 2019/3/6.
 */

@Configuration
@PropertySources(
        @PropertySource(value = {"classpath:config-db.properties"})
)
public class PropertySourcesConfigClass {
}
