package com.example.demo.dao;

import com.example.demo.model.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 评论管理接口
* */
@Mapper
@Repository
public interface ICommentMapper {
    //根据文章id查评论，只查审核通过了的评论
    List<Comment> findCommentByArticleId(Integer articleId);
    //根据评论id查评论
    Comment  findCommentByCommentId(Integer commentId);
    //添加评论
    Integer addComment(Comment comment);
    //删除评论
    @Delete("delete from t_comment where id = #{commentId}")
    boolean delComment(Integer commentId);
    //审核评论
    @Update("update t_comment set status = #{status} where id = #{id}")
    Integer auditComment(Comment comment);
    //查所有评论
    List<Comment> findAllCommentByArticleId(Integer articleId);
    //统计记录数
    @Select("select count(*) from t_comment")
    Integer countArticle();
    //分页查询
    List<Comment> findArticlePage(@Param("begin") Integer begin, @Param("counts") Integer counts);
}
