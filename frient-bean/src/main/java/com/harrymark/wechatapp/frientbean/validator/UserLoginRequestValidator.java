package com.harrymark.wechatapp.frientbean.validator;

import com.haoweima.utils.validate.common.CheckResult;
import com.haoweima.utils.validate.common.ResultEnum;
import com.haoweima.utils.validate.validator.Validator;

/**
 * Created by haoweima on 2019/3/14.
 */
public enum UserLoginRequestValidator implements Validator {
    JS_CODE;

    @Override
    public CheckResult<?> doValidate(Object obj) {
        CheckResult result = new CheckResult();

        if(obj == null){
            result.setResultCode(ResultEnum.FAIL);
            result.setResultMsg("参数为空");
            return result;
        } else{
            result.setResultCode(ResultEnum.SUCCESS);
            result.setResultMsg("成功");
            return result;
        }
    }
}
