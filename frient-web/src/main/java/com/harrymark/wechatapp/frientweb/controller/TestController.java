package com.harrymark.wechatapp.frientweb.controller;

import com.google.gson.Gson;
import com.harrymark.wechatapp.frientbean.po.TestJdbc;
import com.harrymark.wechatapp.frientcommon.utils.HttpUtil;
import com.harrymark.wechatapp.frientcommon.utils.RedisUtil;
import com.harrymark.wechatapp.frientservice.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoweima on 2019/3/7.
 */

@Controller
public class TestController {

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam(required = false, value = "name") String name) {
        List<TestJdbc> testJdbcs = testService.getAll();
        Gson gson = new Gson();
        String json = gson.toJson(testJdbcs.get(0));
        redisUtil.set("test", json);
        redisUtil.set("name", name);
        return json;
    }

    @RequestMapping("/http")
    @ResponseBody
    public String httpTest(){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "马浩伟");
        TestJdbc jsonResult = httpUtil.postApiObject("http://10.100.60.142:8080/test", TestJdbc.class, map);

        return jsonResult.toString();
    }

    @RequestMapping("/gson")
    @ResponseBody
    public String gsonTest(){
        List<TestJdbc> testJdbcs = testService.getAll();
        Gson gson = new Gson();

        return null;
    }

}
