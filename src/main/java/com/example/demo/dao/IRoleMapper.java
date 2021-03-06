package com.example.demo.dao;


import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色数据访问接口
 */
@Mapper
@Repository
public interface IRoleMapper {
    @Select("select r.* from t_role r,t_user u,t_user_role m where m.role_id=r.id and m.user_id=u.id and u.id=#{id}")
    public List<Role> findRoleByLoginUser(User user);//根据用户名查角色
}
