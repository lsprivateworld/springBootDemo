package com.example.springbootdemo.service.impl;


import com.example.springbootdemo.mapper.ArticleMapper;
import com.example.springbootdemo.domin.Article;
import com.example.springbootdemo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Article findArticleById(Integer id) {
        id = 1;
        Article article = articleMapper.findArticleById(id);
        if (article != null) {
            System.out.println(article);
        } else {
            System.out.println("编号为[" + id + "]的文章未找到！");
        }
        return article;
    }

    @Override
    public int updateArticle(Article article) {
       return articleMapper.updateArticle(article);
    }
}