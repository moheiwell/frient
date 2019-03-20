package com.harrymark.wechatapp.frientbean.validator;

import com.haoweima.utils.validate.common.CheckResult;
import com.haoweima.utils.validate.common.ResultEnum;
import com.haoweima.utils.validate.validator.Validator;

/**
 * Created by haoweima on 2019/3/18.
 */
public enum UserInfoRequestValidator implements Validator {
    OPEN_ID,
    NICK_NAME,
    AVATAR_URL,
    GENDER{
        @Override
        public CheckResult<?> doValidate(Object obj) {
            CheckResult result = new CheckResult();
            if(obj != null && "0, 1, 2".contains(obj.toString())){
                result.setResultCode(ResultEnum.SUCCESS);
                result.setResultMsg("成功");
                return result;
            } else{
                result.setResultCode(ResultEnum.FAIL);
                result.setResultMsg("参数不是合法值");
                return result;
            }
        }
    },
    COUNTRY,
    PROVINCE,
    CITY,
    LANGUAGE{
        @Override
        public CheckResult<?> doValidate(Object obj) {
            CheckResult result = new CheckResult();
            if(obj != null && "en, zh_CN, zh_TW".contains(obj.toString())){
                result.setResultCode(ResultEnum.SUCCESS);
                result.setResultMsg("成功");
                return result;
            } else{
                result.setResultCode(ResultEnum.FAIL);
                result.setResultMsg("参数不是合法值");
                return result;
            }
        }
    };

    @Override
    public CheckResult<?> doValidate(Object obj) {
        CheckResult result = new CheckResult();
        result.setResultCode(ResultEnum.SUCCESS);
        return result;
    }
}
