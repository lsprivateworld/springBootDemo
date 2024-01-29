package com.example.springbootdemo.service;

import com.example.springbootdemo.domin.Article;

public interface ArticleService {
    Article findArticleById(Integer id);
    int updateArticle(Article article);
}
