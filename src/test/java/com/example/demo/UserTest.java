package com.example.demo;

import com.example.demo.dao.IUserMapper;
import com.example.demo.model.entity.User;
import com.example.demo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

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
        user.setName("Test2132");
        user.setLoginName("DEL3");
        user.setLoginPass("dev2020");
        user.setEmail("38213QWD37@qq.com");
        user.setRegisterTime(new Date());
        user.setValid(true);
        String msg = iUserService.addUser(user);
        System.err.println(msg);
    }

     //测试按登陆名查找用户
     @Test
     void testFindByLoginName() {
        User user = iUserService.findByLoginName ("TBWORK");
        if(user == null){
            System.err.println("没有相关数据!");
            return;
        }

        System.out.println(user.toString());

     }
     //测试找所有用户
     @Test
    void testFindAllUser () {
        List<User> list = iUserService.findAllUser();

        for(User user : list){
            System.out.println(user.toString());
        }
     }

     //测试修改用户
    @Test
    void testModifyUser(){
        User user = iUserMapper.findByLoginName("DEL3");
        if (user==null){
            System.err.println("用户不存在!");
            return;
        }else {
            System.err.println(user.toString());
        }
        user.setName("用户名修改测试01");
        iUserService.modifyUser(user);
        User user1 = iUserService.findByLoginName("DEL3");
        if(user1 != null ){
            System.err.println(user1.toString());
        }
    }

    //测试删除用户
    @Test
    void testDelUser(){
        User user = iUserService.findByLoginName("DEL3");
        if(user==null){
            System.err.println("找不到对应用户的数据");
            return;
        }
        iUserService.delUser(user);
//        System.out.println(iUserService.delUser(user));

    }





}
