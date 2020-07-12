package com.example.demo.service;

import com.example.demo.model.entity.Comment;
import com.example.demo.util.Page;
import com.example.demo.util.Pager;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/*
 *评论服务接口
 * */
public interface ICommentService {
    //根据博文查询评论，仅查审核过了的
    List<Comment> findCommentByArticleId(Integer articleId);
    //根据评论id查询评论
    Comment findCommentByCommentId(Integer commentId);
    //添加评论
    Integer addComment(Comment comment);
    //删除评论
    boolean delComment(Integer commentId);
    //审核评论
    @Update("update t_comment set status = #{comment.status}")
    Integer auditComment(Comment comment);
    //查所用博文对应的评论
    List<Comment> findAllCommentByArticleId(Integer articleId);
    //分页查询
    Pager findCommentPage(Page page);
}
