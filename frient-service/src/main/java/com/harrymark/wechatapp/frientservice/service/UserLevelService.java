package com.harrymark.wechatapp.frientservice.service;

import com.harrymark.wechatapp.frientbean.dto.request.UserLevelRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.request.UserLevelResponseDTO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;

import java.math.BigDecimal;

/**
 * Created by haoweima on 2019/4/18.
 */

public interface UserLevelService {

    /**
     * 获取用户vip等级
     * @param request
     * @param response
     * @return
     */
    BufferResponse<UserLevelResponseDTO> getUserLevel(BufferRequest<UserLevelRequestDTO> request, BufferResponse<UserLevelResponseDTO> response);

    /**
     * 增加用户的消费积分
     * @param userId
     * @param money
     */
    boolean addUserLevelScore(int userId, BigDecimal money);
}
