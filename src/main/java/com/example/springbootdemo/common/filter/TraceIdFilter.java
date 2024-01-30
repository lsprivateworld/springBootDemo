package com.example.springbootdemo.common.filter;

import com.example.springbootdemo.common.constants.Constant;
import com.example.springbootdemo.common.utils.mdc.MdcUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter是Java Servlet 规范定义的一种过滤器接口，它的主要作用是在 Servlet 容器中对请求和响应进行拦截和处理，
 * 实现对请求和响应的预处理、后处理和转换等功能。通过实现 Filter 接口，开发人员可以自定义一些过滤器来实现各种功能，
 * 如身份验证、日志记录、字符编码转换、防止 XSS 攻击、防止 CSRF 攻击等。那么这里我们使用它对请求做MDC赋值处理。
 */

@Component
public class TraceIdFilter implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException, IOException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String traceId = httpServletRequest.getHeader(Constant.TRACE_ID);
            if (StringUtils.isBlank(traceId)) {
                MdcUtil.setTraceId(MdcUtil.generateUuId());
            } else {
                MdcUtil.setTraceId(traceId);
            }
            // 继续将请求传递给下一个过滤器或目标资源（比如Controller）
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MdcUtil.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}