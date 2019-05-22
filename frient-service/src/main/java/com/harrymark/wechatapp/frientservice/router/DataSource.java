package com.harrymark.wechatapp.frientservice.router;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    String name() default DataSource.dsOne;

    String dsOne = "dsOne";
    String dsTwo = "dsTwo";
    String dsThree = "dsThree";
}
