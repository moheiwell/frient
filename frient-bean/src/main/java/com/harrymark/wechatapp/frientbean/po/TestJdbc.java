package com.harrymark.wechatapp.frientbean.po;

import lombok.Data;

/**
 * Created by haoweima on 2019/3/7.
 */

@Data
public class TestJdbc {

    private int id;
    private String name;
    private String title;

    @Override
    public String toString(){
        return "id: " + id + " name: " + name + " title:" + title;
    }
}
