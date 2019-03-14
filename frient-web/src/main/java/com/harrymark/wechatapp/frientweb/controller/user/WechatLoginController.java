package com.harrymark.wechatapp.frientweb.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信授权登陆
 * Created by haoweima on 2019/3/14.
 */

@Controller
@RequestMapping("/login")
public class WechatLoginController {

    private static final Logger logger = LoggerFactory.getLogger(WechatLoginController.class);

    //小程序 appId
    @Value("${user.login.appId}")
    private String appId;

    //小程序 appSecret
    @Value("${user.login.appSecret}")
    private String appSecret;

    @ResponseBody
    @RequestMapping("/code2Session")
    public String wechatLogin(@RequestParam("js_code") String js_code){
        logger.info("调用code2Session接口登陆，入参为{}", js_code);

        return null;
    }
}
