package com.harrymark.wechatapp.frientbean.enums;

/**
 * Created by haoweima on 2019/4/18.
 */
public enum UserLevelEnum {

    NORMAL("普通会员", ""),
    GOLD("黄金会员", ""),
    DIAMONDS("钻石会员", "");

    UserLevelEnum(String name, String privilege) {
        this.name = name;
        this.privilege = privilege;
    }

    private String name;
    private String privilege;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
