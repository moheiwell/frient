package com.harrymark.wechatapp.frientservice.service.impl;

import com.harrymark.wechatapp.frientbean.po.TestJdbc;
import com.harrymark.wechatapp.frientdao.dao.JDBCTest;
import com.harrymark.wechatapp.frientservice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by haoweima on 2019/3/7.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private JDBCTest jdbcTest;

    @Override
    public List<TestJdbc> getAll() {
        return jdbcTest.selectTest();
    }
}
