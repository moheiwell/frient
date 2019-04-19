package com.harrymark.wechatapp.frientdao.dao;

import com.harrymark.wechatapp.frientbean.po.UserLevelDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by haoweima on 2019/4/19.
 */

@Mapper
@Component
public interface UserLevelMapper {

    int insertUserLevelDetail(UserLevelDetailPO userLevelDetailPO);
}
