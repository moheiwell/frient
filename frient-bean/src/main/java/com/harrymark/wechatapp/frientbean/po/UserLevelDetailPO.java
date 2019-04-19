package com.harrymark.wechatapp.frientbean.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by haoweima on 2019/4/19.
 */

@Data
public class UserLevelDetailPO {
    private int id;
    private int userId;
    private int oldScore;
    private int nowScore;
    private String orderId;
    private BigDecimal orderMoney;
}
