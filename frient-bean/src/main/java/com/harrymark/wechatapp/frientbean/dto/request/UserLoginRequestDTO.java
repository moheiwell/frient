package com.harrymark.wechatapp.frientbean.dto.request;

import com.haoweima.utils.validate.validator.Calibrator;
import lombok.Data;

/**
 * Created by haoweima on 2019/3/14.
 */

@Data
@Calibrator(location = "com.harrymark.wechatapp.frientbean.dto.request.UserLoginRequestValidator")
public class UserLoginRequestDTO {
    private String js_code;
}
