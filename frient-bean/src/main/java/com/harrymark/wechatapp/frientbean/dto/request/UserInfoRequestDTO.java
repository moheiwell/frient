package com.harrymark.wechatapp.frientbean.dto.request;

import com.haoweima.utils.validate.validator.Calibrator;
import lombok.Data;

/**
 * Created by haoweima on 2019/3/15.
 */

@Calibrator(location = "com.harrymark.wechatapp.frientbean.validator.UserInfoRequestValidator")
public class UserInfoRequestDTO {
    private String userId;
    private UserInfo userInfo;//用户信息对象，不包含 openid 等敏感信息
    private String rawData;//不包括敏感信息的原始数据字符串，用于计算签名
    private String signature;//签名
    private String encryptedData;//包括敏感数据在内的完整用户信息的加密数据
    private String iv;//加密算法的初始向量

    static class UserInfo{
        private String nickName;//用户昵称
        private String avatarUrl;//用户头像图片的 URL
        private int gender;//用户性别 1男 2女
        private String country;//用户所在国家
        private String province;//用户所在省份
        private String city;//用户所在城市
        private String language;//语言

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
