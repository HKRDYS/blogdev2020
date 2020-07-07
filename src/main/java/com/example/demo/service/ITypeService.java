package com.example.demo.service;

import com.example.demo.model.entity.Type;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 博文类型服务类
* */
@Service
public interface ITypeService {
    public List<Type> findType();
}
