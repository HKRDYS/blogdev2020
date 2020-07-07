package com.example.demo.dao;

import com.example.demo.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
* 博文数据访问接口
* */
@Mapper
@Repository
public interface IArticleMapper {
    //发表博文
    public boolean publishArticle(Article article);
    //查找所有博文
    public List<Article> findAllArticles();
    //查找博文
    public List<Article> findArticle(Article article);
}
