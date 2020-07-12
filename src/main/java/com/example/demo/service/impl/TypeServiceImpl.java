package com.example.demo.service.impl;

import com.example.demo.dao.ITypeMapper;
import com.example.demo.model.entity.Type;
import com.example.demo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博文类型服务接口实现类
 */
@Service
@Transactional
public class TypeServiceImpl implements ITypeService {
    @Autowired
    private ITypeMapper iTypeMapper;//注入博文类型访问接口
    @Override
    public List<Type> findType() {
        return iTypeMapper.findType();
    }
}
