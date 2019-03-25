package com.harrymark.wechatapp.frientservice.service.impl;

import com.google.gson.Gson;
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
import com.harrymark.wechatapp.frientcommon.utils.WechatAESUtils;
import com.harrymark.wechatapp.frientdao.dao.UserInfoMapper;
import com.harrymark.wechatapp.frientservice.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.script.DigestUtils;
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

    @Autowired
    private Gson gson;

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
     *
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
        if (userInfoPO == null) {
            //新用户登陆
            userInfoPO = new UserInfoPO();
            userInfoPO.setOpenId(httpRes.getOpenid());
            userInfoPO.setSessionKey(httpRes.getSession_key());
            Date time = new Date();
            userInfoPO.setCreateTime(time);
            userInfoPO.setLoginTime(time);
            userInfoMapper.insertUserInfo(userInfoPO);
            resBody.setUserId(userInfoPO.getUserId());
        } else {
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
        UserInfoPO userInfoPO = userInfoMapper.getUserInfoByUserId(request.getBody().getUserId());
        UserInfoRequestDTO reqBody = request.getBody();
        String sha1 = DigestUtils.sha1DigestAsHex(reqBody.getRawData() + userInfoPO.getSessionKey());

        if (!sha1.equals(reqBody.getSignature())) {
            logger.error("用户信息落地接口，sha1校验失败");
            response.setResult(HttpResultEnum.FAILED);
            return response;
        }

        UserInfoPO userInfo = null;
        try {
            String decode = WechatAESUtils.decryptForWeChatApplet(reqBody.getEncryptedData(), userInfoPO.getSessionKey(), reqBody.getIv());
            logger.info("EncryptedData解密成功:{}", decode);
            userInfo = gson.fromJson(decode, UserInfoPO.class);
        } catch (Exception e) {
            logger.error("EncryptedData解密失败{}", e);
            response.setResult(HttpResultEnum.FAILED);
            return response;
        }

        userInfo.setUserId(userInfoPO.getUserId());
        int result = userInfoMapper.updateUserInfo(userInfo);
        if (result == 0) {
            response.setResult(HttpResultEnum.FAILED);
            return response;
        }

        return response;
    }
}
