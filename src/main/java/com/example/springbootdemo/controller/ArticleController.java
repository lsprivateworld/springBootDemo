package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.springbootdemo.common.annotation.LogAnnotation;
import com.example.springbootdemo.common.logger.MyLogger;
import com.example.springbootdemo.domin.Article;
import com.example.springbootdemo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/getArticle")
    @LogAnnotation
    public String getArticle(){
        ModelAndView modelAndView = new ModelAndView("articleList");
        Integer id = 1;
        Article article = articleService.findArticleById(id);
        return JSON.toJSONString(article);
    }

    @RequestMapping("/insertArticle")
    @LogAnnotation
    public String insertArticle(){
        Article article = Article.builder().title("第一篇文章").content("试试看咯").build();
        articleService.insertArticle(article);
        return JSON.toJSONString(article);
    }

}
