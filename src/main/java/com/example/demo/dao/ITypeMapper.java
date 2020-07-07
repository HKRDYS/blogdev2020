package com.example.demo.dao;

import com.example.demo.model.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 博文类型数据访问接口
* */
@Mapper
@Repository
public interface ITypeMapper {
    @Select("select * from t_type")
    public List<Type> findType();
}
