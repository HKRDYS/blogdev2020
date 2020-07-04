package com.example.demo.dao;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

//角色数据访问接口
@Mapper
@Repository
public interface IRoleMapper {
    @Select("select role from t_role where id=(select role_id from t_user_role where user_id = #{id} )")
    public List<Role> findRoleByLoginUser(User user);//根据用户名查角色
}
