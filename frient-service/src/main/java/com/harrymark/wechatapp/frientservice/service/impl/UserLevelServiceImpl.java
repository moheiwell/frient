package com.harrymark.wechatapp.frientservice.service.impl;

import com.harrymark.wechatapp.frientbean.dto.request.UserLevelRequestDTO;
import com.harrymark.wechatapp.frientbean.dto.request.UserLevelResponseDTO;
import com.harrymark.wechatapp.frientbean.enums.UserLevelEnum;
import com.harrymark.wechatapp.frientbean.po.UserInfoPO;
import com.harrymark.wechatapp.frientbean.po.UserLevelDetailPO;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferRequest;
import com.harrymark.wechatapp.frientcommon.httpUtils.BufferResponse;
import com.harrymark.wechatapp.frientdao.dao.UserInfoMapper;
import com.harrymark.wechatapp.frientdao.dao.UserLevelMapper;
import com.harrymark.wechatapp.frientservice.service.UserLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by haoweima on 2019/4/18.
 */

@Service
public class UserLevelServiceImpl implements UserLevelService {

    private static final Logger logger = LoggerFactory.getLogger(UserLevelServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserLevelMapper userLevelMapper;

    /**
     * 获取用户vip等级
     * @param request
     * @param response
     * @return
     */
    @Override
    public BufferResponse<UserLevelResponseDTO> getUserLevel(BufferRequest<UserLevelRequestDTO> request,
                                                             BufferResponse<UserLevelResponseDTO> response) {
        UserLevelResponseDTO responseDTO = new UserLevelResponseDTO();

        UserInfoPO userInfoPO = userInfoMapper.getUserInfoByUserId(request.getBody().getUserId() + "");
        responseDTO.setUserId(request.getBody().getUserId());
        int score = userInfoPO.getUserLevel();
        responseDTO.setUserLevelScore(score);
        if(score <= 3000){
            //普通会员
            responseDTO.setUserLevel(UserLevelEnum.NORMAL.getName());
            responseDTO.setPrivilege(UserLevelEnum.NORMAL.getPrivilege());
        }else if(score <= 10000 ){
            //黄金会员
            responseDTO.setUserLevel(UserLevelEnum.GOLD.getName());
            responseDTO.setPrivilege(UserLevelEnum.GOLD.getPrivilege());
        }else {
            //钻石会员
            responseDTO.setUserLevel(UserLevelEnum.DIAMONDS.getName());
            responseDTO.setPrivilege(UserLevelEnum.DIAMONDS.getPrivilege());
        }
        response.setBody(responseDTO);
        return response;
    }

    @Override
    public boolean addUserLevelScore(int userId, BigDecimal money) {
        logger.info("开始记录用户{}积分变化，消费金额为{}", userId, money);
        UserInfoPO userInfoPO = userInfoMapper.getUserInfoByUserId(userId + "");
        int score = userInfoPO.getUserLevel() + money.divide(new BigDecimal(10), 0, BigDecimal.ROUND_UP).intValue();

        UserLevelDetailPO userLevelDetail = new UserLevelDetailPO();
        userLevelDetail.setUserId(userId);
        userLevelDetail.setOldScore(userInfoPO.getUserLevel());
        userLevelDetail.setNowScore(score);
        userLevelMapper.insertUserLevelDetail(userLevelDetail);

        UserInfoPO updateScore = new UserInfoPO();
        updateScore.setUserId(userId);
        updateScore.setUserLevel(score);
        int isSuccess = userInfoMapper.updateUserInfo(updateScore);
        if(isSuccess != 1){
            logger.error("用户{}积分增加失败", userId);
            return false;
        }

        return true;
    }
}
