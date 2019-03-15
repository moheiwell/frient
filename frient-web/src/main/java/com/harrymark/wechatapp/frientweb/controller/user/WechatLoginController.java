package com.harrymark.wechatapp.frientweb.controller.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoweima.utils.validate.common.CheckResult;
import com.haoweima.utils.validate.common.ResultEnum;
import com.haoweima.utils.validate.validator.CheckHander;
import com.harrymark.wechatapp.frientbean.dto.request.UserLoginRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserLoginResponseDTO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;
import com.harrymark.wechatapp.frientcommon.httpUtils.HttpResultEnum;
import com.harrymark.wechatapp.frientservice.service.WechatLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private Gson gson;

    @Autowired
    private WechatLoginService wechatLoginService;

    @ResponseBody
    @RequestMapping("/code2Session")
    public String wechatLogin(@RequestParam("reqData") String reqData){
        logger.info("调用code2Session接口登陆，入参为{}", reqData);
        BufferResponse<UserLoginResponseDTO> response = new BufferResponse<>();

        try {
            BufferRequest<UserLoginRequestDTO> request = gson.fromJson(reqData, new TypeToken<BufferRequest<UserLoginRequestDTO>>(){}.getType());
            CheckResult checkResult = CheckHander.hander(request.getBody());

            if(checkResult.getResult() == ResultEnum.FAIL){
                logger.error("调用code2Session接口登陆，参数校验错误！" + checkResult.getResultMsg());
                response.setResult(HttpResultEnum.FAILED);
                response.setBody(new UserLoginResponseDTO());
                return gson.toJson(response);
            }

            response = wechatLoginService.getOpenId(request, response);

        } catch (Exception e) {
            logger.info("调用code2Session接口登陆失败" + e);
        }
        return response.getHeader().getResponseMsg();
    }
}
