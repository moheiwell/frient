package com.harrymark.wechatapp.frientservice.service.impl;

import com.harrymark.wechatapp.frientbean.dto.request.UserLoginRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserLoginResponseDTO;
import com.harrymark.wechatapp.frientbean.po.UserInfoPO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;
import com.harrymark.wechatapp.frientcommon.httpUtils.HttpResultEnum;
import com.harrymark.wechatapp.frientcommon.httpUtils.HttpUtil;
import com.harrymark.wechatapp.frientdao.dao.UserInfoMapper;
import com.harrymark.wechatapp.frientservice.service.WechatLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haoweima on 2019/3/14.
 */

@Service
public class WechatLoginServiceImpl implements WechatLoginService {
    private static final Logger logger = LoggerFactory.getLogger(WechatLoginServiceImpl.class);

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private UserInfoMapper userInfoMapper;

    //小程序 appId
    @Value("${user.login.appId}")
    private String appId;

    //小程序 appSecret
    @Value("${user.login.appSecret}")
    private String appSecret;

    //code2Session地址
    @Value("${user.login.url}")
    private String url;

    @Override
    public BufferResponse<UserLoginResponseDTO> getOpenId(BufferRequest<UserLoginRequestDTO> request, BufferResponse<UserLoginResponseDTO> response) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("appid", appId);
        reqMap.put("secret", appSecret);
        reqMap.put("js_code", request.getBody().getJs_code());
        reqMap.put("grant_type", "authorization_code");
        UserLoginResponseDTO respBody = httpUtil.getApiObjectHttps(url, UserLoginResponseDTO.class, reqMap);
        if (respBody == null) {
            response.setResult(HttpResultEnum.FAILED);
            return response;
        }

        UserInfoPO userInfoPO = userInfoMapper.getUserInfoByOpenId(respBody.getOpenid());
        if(userInfoPO == null){
            //新用户登陆
            userInfoPO = new UserInfoPO();
            userInfoPO.setOpenId(respBody.getOpenid());
            Date time = new Date();
            userInfoPO.setCreateTime(time);
            userInfoPO.setLoginTime(time);
            userInfoMapper.insertUserInfo(userInfoPO);
        }else{
            //老用户登陆
            userInfoPO.setLoginTime(new Date());
            userInfoMapper.updateUserInfo(userInfoPO);
        }
        response.setBody(respBody);
        response.setResult(HttpResultEnum.SUCCESS);
        return response;
    }
}
