package com.example.demo.service;

import com.example.demo.model.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 博文服务层接口
* */
@Service
public interface IArticleService {
    //发表博文
    String publishArticle(Article article);
    //查找所有博文
    List<Article> findALLArticles();
    //查找博文
    List<Article> findArticle(Article article);
}
