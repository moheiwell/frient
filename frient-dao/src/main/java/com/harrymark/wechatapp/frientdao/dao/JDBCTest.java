package com.harrymark.wechatapp.frientdao.dao;

import com.harrymark.wechatapp.frientbean.po.TestJdbc;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by haoweima on 2019/3/7.
 */

@Mapper
@Component
public interface JDBCTest {
    List<TestJdbc> selectTest();
}
