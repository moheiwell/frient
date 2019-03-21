package com.harrymark.wechatapp.frientservice.service.impl;

import com.harrymark.wechatapp.frientbean.dto.request.UserInfoRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.request.UserLoginRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserInfoResponseDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserLoginResponseDTO;
import com.harrymark.wechatapp.frientbean.po.UserInfoPO;
import com.harrymark.wechatapp.frientbean.wechatdto.Code2SessionResponse;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;
import com.harrymark.wechatapp.frientcommon.httpUtils.HttpResultEnum;
import com.harrymark.wechatapp.frientcommon.httpUtils.HttpUtil;
import com.harrymark.wechatapp.frientdao.dao.UserInfoMapper;
import com.harrymark.wechatapp.frientservice.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

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

    /**
     * 请求微信服务器，获得取到openid和SessionKey
     * @param request
     * @param response
     * @return
     */
    @Override
    public BufferResponse<UserLoginResponseDTO> getOpenId(BufferRequest<UserLoginRequestDTO> request, BufferResponse<UserLoginResponseDTO> response) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("appid", appId);
        reqMap.put("secret", appSecret);
        reqMap.put("js_code", request.getBody().getJs_code());
        reqMap.put("grant_type", "authorization_code");
        Code2SessionResponse httpRes = httpUtil.getApiObjectHttps(url, Code2SessionResponse.class, reqMap);
        if (httpRes == null || (httpRes.getErrcode() != null && !"0".equals(httpRes.getErrcode()))) {
            response.setResult(HttpResultEnum.FAILED);
            return response;
        }

        UserLoginResponseDTO resBody = new UserLoginResponseDTO();
        UserInfoPO userInfoPO = userInfoMapper.getUserInfoByOpenId(httpRes.getOpenid());
        if(userInfoPO == null){
            //新用户登陆
            userInfoPO = new UserInfoPO();
            userInfoPO.setOpenId(httpRes.getOpenid());
            userInfoPO.setSessionKey(httpRes.getSession_key());
            Date time = new Date();
            userInfoPO.setCreateTime(time);
            userInfoPO.setLoginTime(time);
            userInfoMapper.insertUserInfo(userInfoPO);
            resBody.setUserId(userInfoPO.getUserId());
        }else{
            //老用户登陆
            userInfoPO.setSessionKey(httpRes.getSession_key());
            userInfoPO.setLoginTime(new Date());
            userInfoMapper.updateUserInfo(userInfoPO);
            resBody.setUserId(userInfoPO.getUserId());
        }

        response.setBody(resBody);
        response.setResult(HttpResultEnum.SUCCESS);
        return response;
    }

    @Override
    public BufferResponse<UserInfoResponseDTO> saveUserInfo(BufferRequest<UserInfoRequestDTO> request, BufferResponse<UserInfoResponseDTO> response) {
        UserInfoPO userInfoPO = new UserInfoPO();
        BeanUtils.copyProperties(request.getBody(), userInfoPO);

        int result = userInfoMapper.updateUserInfo(userInfoPO);

        if(result == 0){
            response.setResult(HttpResultEnum.FAILED);
            return response;
        }

        UserInfoPO resUserInfo = userInfoMapper.getUserInfoByOpenId(userInfoPO.getOpenId());
        BeanUtils.copyProperties(resUserInfo, response.getBody());
        response.setResult(HttpResultEnum.SUCCESS);

        return response;
    }
}
