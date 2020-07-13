package com.example.demo.model.entity;

import java.io.Serializable;

public class Statistic implements Serializable {
    private Integer id;
    private Integer article_id;
    private String hints;
    private Integer comments_num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public Integer getComments_num() {
        return comments_num;
    }

    public void setComments_num(Integer comments_num) {
        this.comments_num = comments_num;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", article_id=" + article_id +
                ", hints='" + hints + '\'' +
                ", comments_num=" + comments_num +
                '}';
    }
}
