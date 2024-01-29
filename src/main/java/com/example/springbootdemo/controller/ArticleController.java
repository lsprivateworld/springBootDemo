package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.springbootdemo.domin.Article;
import com.example.springbootdemo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/getArticle")
    public String getFlowList(){
        ModelAndView modelAndView = new ModelAndView("articleList");
        Integer id = 1;
        Article article = articleService.findArticleById(id);
        return JSON.toJSONString(article);
    }

}
