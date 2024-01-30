package com.example.springbootdemo.utils.mdc;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 配置日志过滤器
 * @author liushuai
 * @date 2024.01.29
 */
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<MdcTraceFilter> registrationTrackFilter() {

        FilterRegistrationBean<MdcTraceFilter> filterRegistrationBean = new FilterRegistrationBean<MdcTraceFilter>();
        filterRegistrationBean.setFilter(new MdcTraceFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("trackFilter");
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
