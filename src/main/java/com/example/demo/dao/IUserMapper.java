package com.example.demo.dao;

import com.example.demo.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* user数据访问接口
* 一.使用java注解方式
* 1.   4个注解
* @Insert
* @Delete
* @Update
* @Select
* 2.优点：简单
* 3.缺点：不能使用动态sql
*
* */
@Mapper
@Repository
public interface IUserMapper {
    /*
    * 注意此处的User是自定义在实体类下的user不是spring安全框架中的user
    * 次注解定义的userMap，后边可以引用，如果配置了驼峰命名规则（或实体类与表字段完全一致）
    * 可省略，弹药注意实体类与数据库表字段之间的严格对应关系，入login_name->loginName
    *
    * */
    @Results(id = "userMap", value = {
            @Result(id = true , column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "login_name",property = "loginName"),
            @Result(column = "login_pass",property = "loginPass"),
            @Result(column = "email",property = "email"),
            @Result(column = "valid",property = "valid"),
            @Result(column = "img",property = "img")
    })
    @Select("select * from t_user where login_name=#{loginName}")
    public User findByLoginName(String loginName);//按照登录名查询用户，用于登录验证
    //为了更方便灵活使用各种查询语句，除上面一个方法外，其他所有方法均使用mapper.xml配置方式
    public List<User> findAllUser();//查找所有用户
    public int delUser(User user);//删除指定用户，删除成功返回记录数，删除失败返回0
    public int modifyUser(User user);//修改指定用户，修改成功返回记录数，修改失败返回0
    public List<User> findUser(User user);//多条件查找用户
    public int addUser(User user);//添加用户，注册用户
}
