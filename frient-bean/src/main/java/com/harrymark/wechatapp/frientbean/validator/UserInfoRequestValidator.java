package com.harrymark.wechatapp.frientbean.validator;

import com.haoweima.utils.validate.common.CheckResult;
import com.haoweima.utils.validate.common.ResultEnum;
import com.haoweima.utils.validate.validator.Validator;

/**
 * Created by haoweima on 2019/3/18.
 */
public enum UserInfoRequestValidator implements Validator {
    userId,
    userInfo,
    rawData,
    signature,
    encryptedData,
    iv;

    @Override
    public CheckResult<?> doValidate(Object obj) {
        CheckResult result = new CheckResult();
        result.setResultCode(ResultEnum.SUCCESS);
        return result;
    }
}
