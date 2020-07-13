package com.example.demo;


import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.User;
import com.example.demo.service.ICommentService;
import com.example.demo.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class CommentTest {
    @Autowired
    private ICommentService iCommentService;
    @Test
    void addComment(){
        Comment comment = new Comment();
        Article article = new Article();
        article.setId(1);
        comment.setArticle(article);
        comment.setStatus("1");
        comment.setCommentTime(new Date());
        comment.setContent("Text for a test");
        comment.setIp("0.0.0.0");
        User user = new User();
        user.setId(1);
        comment.setUser(user);
        Integer v = iCommentService.addComment(comment);
        if(v==1){
            System.err.println("添加成功");
        }
    }

    @Test
    void findCommentByArticleId() {
        List<Comment> list = iCommentService.findCommentByArticleId(1);
        for (Comment comment : list) {
            System.out.println(comment.toString());
        }
    }
    @Test
    void findCommentByCommentId() {
        Comment comment = iCommentService.findCommentByCommentId(1);
        System.out.println(comment.toString());
        }

    @Test
    void auditComment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setStatus("-1");
        Integer n = iCommentService.auditComment(comment);
        System.out.println(n == 1 ? "审核成功" : "审核失败");
    }
    @Test
    void delComment() {
        Boolean flag = iCommentService.delComment(1);
        System.out.println(flag ? "删除成功" : "删除失败");
    }
}
