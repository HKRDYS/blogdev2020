package com.example.demo.service.impl;

import com.example.demo.dao.ICommentMapper;
import com.example.demo.model.entity.Comment;
import com.example.demo.service.ICommentService;
import com.example.demo.util.Page;
import com.example.demo.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 评论管理服务实现类
* */
@Service
public class ICommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentMapper iCommentMapper;
    //只查审核通过的评论
    @Override
    public List<Comment> findCommentByArticleId(Integer articleId) {
        return iCommentMapper.findCommentByArticleId(articleId);
    }

    @Override
    public Comment findCommentByCommentId(Integer commentId) {
        return iCommentMapper.findCommentByCommentId(commentId);
    }

    @Override
    public Integer addComment(Comment comment) {
        return iCommentMapper.addComment(comment);
    }

    @Override
    public boolean delComment(Integer commentId) {
        return iCommentMapper.delComment(commentId);
    }

    @Override
    public Integer auditComment(Comment comment) {
        return iCommentMapper.auditComment(comment);
    }
//查全部评论
    @Override
    public List<Comment> findAllCommentByArticleId(Integer articleId) {
        return iCommentMapper.findCommentByArticleId(articleId);
    }
//分页查找
    @Override
    public Pager findCommentPage(Page page) {
        Pager pager = new Pager();
        //查出总记录数
        Integer totalRecords = iCommentMapper.countArticle();
        //page对象永远不会为null，只能通过其属性来判断，严格讲所有属性都要判断，值做简化处理
        Page page1 = null;//page与page1是2个不同的对象
        if (page == null || page.getCurrentPage() == null){
            page1 = new Page(totalRecords,0);
        }else {
            page1 = new Page(totalRecords,page.getCurrentPage());
        }
        //从当前页面的第一条记录开叉，查记录数为页面大小
        List<Comment> list = iCommentMapper.findArticlePage(page1.getCurrentPage(),page1.getPageSize());
        pager.setPage(page1);
        pager.setList(list);
        return pager;
    }
}
