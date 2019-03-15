package com.harrymark.wechatapp.frientdao.dao;

import com.harrymark.wechatapp.frientbean.po.UserInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by haoweima on 2019/3/15.
 */
@Mapper
@Component
public interface UserInfoMapper {

    int insertUserInfo(UserInfoPO userInfoPO);

    UserInfoPO getUserInfoByOpenId(String openId);

    int updateUserInfo(UserInfoPO userInfoPO);
}
