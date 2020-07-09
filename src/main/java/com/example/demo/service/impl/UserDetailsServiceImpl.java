package com.example.demo.service.impl;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.service.IRoleService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//UserDetailsService接口实现
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserServiceImpl iUserService;//注入用户服务接口
    @Autowired
    private RoleServiceImpl iRoleService;//注入角色服务接口
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = iUserService.findByLoginName(s);//根据登录名查用户信息
        if (user != null){
            List<Role> list = iRoleService.findRoleByLoginUser(user);//根据用户查角色
            StringBuilder roles = new StringBuilder();//用于保存角色名
            for (Role r: list){
                roles.append(r.getRole());
            }
            //重新封装成Spring Security 安全框架支持的类型
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = Arrays.asList(roles).stream()
                    .map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
            //z注意是两个User,一个是Spring安全框架中的，一个是自定义的
            return new org.springframework.security.core.userdetails.User(user.getLoginName(),user.getLoginPass(),simpleGrantedAuthorities);

        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
