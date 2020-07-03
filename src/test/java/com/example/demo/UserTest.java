package com.example.demo;

import com.example.demo.dao.IUserMapper;
import com.example.demo.model.entity.User;
import com.example.demo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * User测试类
 * @author HKRDYS
 * @version 1.0
 * Date:2020-07-03
 * */

@SpringBootTest
public class UserTest {
    @Autowired
    private IUserMapper iUserMapper;
    @Autowired
    private IUserService iUserService;

    @Test
    void testAddUser(){
        User user = new User();
        user.setName("Test");
        user.setLoginName("HKRDYS");
        user.setLoginPass("dev2020");
        user.setEmail("3390452267@qq.com");
        user.setRegisterTime(new Date());
        user.setValid(true);
        String msg = iUserService.addUser(user);
        System.out.println(msg);
    }

    @Test
    void testFindUserByName(){

    }
}
