package com.example.demo.service.impl;

import com.example.demo.dao.IArticleMapper;
import com.example.demo.model.entity.Article;
import com.example.demo.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
* 博文服务层接口
* */
@Primary
@Transactional
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private IArticleMapper iArticleMapper;//注入博文数据访问接口
    @Autowired
    private RedisTemplate redisTemplate;//注入redis接口做数据缓存
    @Override
    public String publishArticle(Article article) {
        boolean flag = iArticleMapper.publishArticle(article);
        if (flag){
            // 缓存当前博文
            List<Article> list = iArticleMapper.findArticle(article);
            //清除掉前边缓存的数据
            Object obj = redisTemplate.opsForValue().get("article_list");
            if (obj != null){
                redisTemplate.delete("article_list");
            }
            //重新缓存数据
            redisTemplate.opsForValue().set("article_list",list);
            return "发布成功";
        }else {
            return "发布失败";
        }
    }

    @Override
    public List<Article> findALLArticles() {
        List<Article> list=iArticleMapper.findAllArticles();
        if(list != null && list.size() > 0){
            //清除掉前边缓存的数据
            Object obj=redisTemplate.opsForValue().get("article_list");
            if(obj!=null){
                redisTemplate.delete("article_list");
            }
            //重新缓存数据
            redisTemplate.opsForValue().set("article_list",list);
        }
        return list;
    }

    @Override
    public List<Article> findArticle(Article article) {
        return iArticleMapper.findArticle(article);
    }
}
