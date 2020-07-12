package com.example.demo.service;

import com.example.demo.dao.IArticleMapper;

import com.example.demo.model.entity.Article;
import com.example.demo.util.Page;
import com.example.demo.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 博文服务层接口
 */
public interface IArticleService {
    //发表博文
    String publishArticle(Article article);
    //查找所有博文
    List<Article> findAllArticles();
    //查找博文
    List<Article> findArticle(Article article);
    //分页面查询
    Pager<Article> findArticlePage(Article article, Page page);
    //修改博文
    boolean modifyArticle(Article article);
    //删除博文
    Integer delArticle(Integer id);
    //审核博文
    boolean auditArticle(String status, Integer id);
    //查看博文
    Article findArticleById(Integer id);
    //查找最新审核通过的10篇博文
    List<Article> findNewTop10();
}
