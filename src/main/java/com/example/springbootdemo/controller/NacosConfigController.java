package com.example.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosConfigController {
    @Value(value = "${config.info}")
    private String configInfo;
    @Value("${config.port}")
    private String port;


    @GetMapping("/config/info")
    public String getConfigInfo(){
        return configInfo + "::" + port;
    }
}
