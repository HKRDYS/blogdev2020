package com.example.demo;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.service.IRoleService;
import com.example.demo.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * Role测试类
 * @author HKRDYS
 * @version 1.0
 * Date:2020-07-03
 * */

@SpringBootTest
public class RoleTest {
    @Autowired
    RoleServiceImpl iRoleService;

    @Test
    void testFindRole(){
        User user = new User();
        user.setId(1);
        List<Role> roleList = iRoleService.findRoleByLoginUser(user);
        if (roleList == null){
            System.err.println("\033 找不到相关数据");
            return;
        }
        for (Role r : roleList){
            System.out.println("\036 数据值:"+r.toString());
        }
    }
    @Test
    void UCreat() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123456");
        System.err.println(password);
    }
}
