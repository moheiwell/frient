package com.harrymark.wechatapp.frientweb.controller;

import com.google.gson.Gson;
import com.harrymark.wechatapp.frientbean.po.TestJdbc;
import com.harrymark.wechatapp.frientservice.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by haoweima on 2019/3/7.
 */

@Controller
public class TestController {

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @RequestMapping("/")
    @ResponseBody
    public String test() {
        logger.info("test");
        logger.error("test error");
        List<TestJdbc> testJdbcs = testService.getAll();
        Gson gson = new Gson();
        return gson.toJson(testJdbcs);
    }
}
