package com.harrymark.wechatapp.frientbean.dto.request;

import com.haoweima.utils.validate.validator.Calibrator;
import lombok.Data;

/**
 * Created by haoweima on 2019/3/15.
 */

@Data
@Calibrator(location = "com.harrymark.wechatapp.frientbean.validator.UserInfoRequestValidator")
public class UserInfoRequestDTO {
    private String openId;//唯一标识
    private String nickName;//用户昵称
    private String avatarUrl;//用户头像图片的 URL
    private int gender;//用户性别 1男性 2女性
    private String country;//用户所在国家
    private String province;//用户所在省份
    private String city;//用户所在城市
    private String language;//显示 country，province，city 所用的语言
}
