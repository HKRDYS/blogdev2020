package com.example.demo.service;

import com.example.demo.model.entity.User;

import java.util.List;

/*
* User服务器接口
* */
public interface IUserService {
    User findByLoginName(String loginName);
    List<User> findAllUser();
    String delUser(User user);
    String modifyUser(User user);
    List<User> findUser(User user);
    String addUser(User user);
}
