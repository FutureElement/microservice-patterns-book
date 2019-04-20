package com.ms.zg.book.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class CustomFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 过滤器类型，pre表示在路由前执行
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 过滤器顺序
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 过滤器开关
        return true;
    }

    @Override
    public Object run() {
        // 获取请求上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取HTTP响应对象
        HttpServletResponse servletResponse = context.getResponse();
        // 在响应中增加定义的头部信息
        servletResponse.addHeader("X-Sample", UUID.randomUUID().toString());
        return null;

    }
}
