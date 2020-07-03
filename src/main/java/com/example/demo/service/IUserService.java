package com.example.demo.service;

import com.example.demo.model.entity.User;

import java.util.List;

/*
* User服务器接口
* */
public interface IUserService {
    public User findByLoginName(String loginName);
    public List<User> findAllUser();
    public String delUser(User user);
    public String modifyUser(User user);
    public List<User> findUser(User user);
    public String addUser(User user);
}
