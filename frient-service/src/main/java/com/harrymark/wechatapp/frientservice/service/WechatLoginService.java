package com.harrymark.wechatapp.frientservice.service;

import com.harrymark.wechatapp.frientbean.dto.request.UserLoginRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserLoginResponseDTO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;

/**
 * Created by haoweima on 2019/3/14.
 */
public interface WechatLoginService {

    BufferResponse<UserLoginResponseDTO> getOpenId(BufferRequest<UserLoginRequestDTO> request, BufferResponse<UserLoginResponseDTO> response);
}
