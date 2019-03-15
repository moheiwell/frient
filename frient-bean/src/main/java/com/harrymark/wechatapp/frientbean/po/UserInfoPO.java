package com.harrymark.wechatapp.frientbean.po;

import lombok.Data;

import java.util.Date;

/**
 * Created by haoweima on 2019/3/15.
 */

@Data
public class UserInfoPO {
    private int userId;
    private String openId;
    private String unionId;
    private String userName;
    private String wechatName;
    private Date loginTime;
    private Date createTime;
}
