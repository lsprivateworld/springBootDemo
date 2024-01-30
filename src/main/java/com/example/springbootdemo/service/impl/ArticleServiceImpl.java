package com.example.springbootdemo.service.impl;


import com.example.springbootdemo.common.logger.MyLogger;
import com.example.springbootdemo.config.RedissonConfig;
import com.example.springbootdemo.mapper.ArticleMapper;
import com.example.springbootdemo.domin.Article;
import com.example.springbootdemo.service.ArticleService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertArticle(Article article) {
        articleMapper.insertArticle(article);
    }
}
