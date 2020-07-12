package com.example.demo.model.entity;

import java.io.Serializable;

/**
 * 角色实体类
 */
public class Role implements Serializable {
    private Integer id;//主键
    private String role;//角色名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}

