package com.example.springbootdemo.service;

import com.example.springbootdemo.domin.Article;
import org.springframework.stereotype.Service;

public interface ArticleService {
    Article findArticleById(Integer id);
    int updateArticle(Article article);
    void insertArticle(Article article);
}
