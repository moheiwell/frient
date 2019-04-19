package com.harrymark.wechatapp.frientbean.dto.request;

import lombok.Data;

/**
 * Created by haoweima on 2019/4/18.
 */

@Data
public class UserLevelResponseDTO {
    private int userId;//用户id
    private int userLevelScore;//用户等级分数
    private String userLevel;//用户等级
    private String privilege;//用户特权
}
