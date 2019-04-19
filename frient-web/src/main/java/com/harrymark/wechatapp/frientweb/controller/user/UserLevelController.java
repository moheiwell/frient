package com.harrymark.wechatapp.frientweb.controller.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoweima.utils.validate.common.CheckResult;
import com.haoweima.utils.validate.common.ResultEnum;
import com.haoweima.utils.validate.validator.CheckHander;
import com.harrymark.wechatapp.frientbean.dto.request.UserLevelRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.request.UserLevelResponseDTO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;
import com.harrymark.wechatapp.frientcommon.httpUtils.HttpResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户vip等级相关接口
 * Created by haoweima on 2019/4/18.
 */

@Controller
@RequestMapping("/userlevel")
public class UserLevelController {

    private static final Logger logger = LoggerFactory.getLogger(UserLevelController.class);

    @Autowired
    private Gson gson;

    /**
     * 获取用户vip等级
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserLevel")
    public String getUserLevel(@RequestParam("reqData") String reqData){
        logger.info("获取用户vip等级接口，入参为{}", reqData);
        BufferResponse<UserLevelResponseDTO> response = new BufferResponse<>();

        try {
            BufferRequest<UserLevelRequestDTO> request = gson.fromJson(reqData, new TypeToken<BufferRequest<UserLevelRequestDTO>>(){}.getType());
            CheckResult checkResult = CheckHander.hander(request.getBody());

            if(checkResult.getResult() == ResultEnum.FAIL){
                logger.error("获取用户vip等级，参数校验错误！" + checkResult.getResultMsg());
                response.setResult(HttpResultEnum.FAILED);
                response.setBody(new UserLevelResponseDTO());
                return gson.toJson(response);
            }
        } catch (Exception e) {
            logger.info("获取用户vip等级失败" + e);
        }
        return gson.toJson(response);
    }
}
