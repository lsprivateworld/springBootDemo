package com.example.springbootdemo.utils.mdc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MdcTraceFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String tid = request.getHeader("tid");
        ThreadMdcUtil.setTraceId(tid);
        // 响应头设置tid
        if (servletResponse instanceof HttpServletResponse) {
            ((HttpServletResponse) servletResponse).addHeader("tid", MdcUtil.getTraceId());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        MdcUtil.removeTraceId();
    }
}
