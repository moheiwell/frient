package com.harrymark.wechatapp.frientservice.service;

import com.harrymark.wechatapp.frientbean.dto.request.UserInfoRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.request.UserLoginRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserInfoResponseDTO;
import com.harrymark.wechatapp.frientbean.dto.response.UserLoginResponseDTO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;

/**
 * Created by haoweima on 2019/3/14.
 */
public interface UserInfoService {

    BufferResponse<UserLoginResponseDTO> getOpenId(BufferRequest<UserLoginRequestDTO> request, BufferResponse<UserLoginResponseDTO> response);

    BufferResponse<UserInfoResponseDTO> saveUserInfo(BufferRequest<UserInfoRequestDTO> request, BufferResponse<UserInfoResponseDTO> response);
}
