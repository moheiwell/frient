package com.harrymark.wechatapp.frientservice.service.impl;

import com.harrymark.wechatapp.frientbean.dto.request.UserLoginRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserLoginResponseDTO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;
import com.harrymark.wechatapp.frientcommon.httpUtils.HttpUtil;
import com.harrymark.wechatapp.frientservice.service.WechatLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Created by haoweima on 2019/3/14.
 */

@Service
public class WechatLoginServiceImpl implements WechatLoginService {
    private static final Logger logger = LoggerFactory.getLogger(WechatLoginServiceImpl.class);

    @Autowired
    private HttpUtil httpUtil;

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
    public void getOpenId(BufferRequest<UserLoginRequestDTO> request, BufferResponse<UserLoginResponseDTO> response) {
        MultiValueMap<String, Object> reqMap = new LinkedMultiValueMap<>();
        reqMap.add("appid", appId);
        reqMap.add("secret", appSecret);
        reqMap.add("js_code", request.getBody().getJs_code());
        reqMap.add("grant_type", "authorization_code");
        UserLoginResponseDTO httpResp = httpUtil.getApiObjectHttps(url, UserLoginResponseDTO.class, reqMap);
    }
}
