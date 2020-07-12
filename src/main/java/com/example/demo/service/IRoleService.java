package com.example.demo.service;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;

import java.util.List;

/**
 * 角色服务类
 */
public interface IRoleService {
    public List<Role> findRoleByLoginUser(User user);
}
