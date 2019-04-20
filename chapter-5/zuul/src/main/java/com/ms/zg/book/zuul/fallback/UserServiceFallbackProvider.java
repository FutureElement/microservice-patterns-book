package com.ms.zg.book.zuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

// 实现FallbackProvider接口，定义路由级别的熔断策略
@Component
public class UserServiceFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        // 路由的名称，通常为serviceId
        return "users-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            // 定义熔断后的响应头
            @Override
            @NonNull
            public HttpHeaders getHeaders() {
                final HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }

            // 定义熔断后的响应Body
            @Override
            @NonNull
            public InputStream getBody() {
                final String goodJson = "{\"error\":\"user service crashed\"}";
                return new ByteArrayInputStream(goodJson.getBytes());
            }

            // 定义熔断后的HTTP响应状态
            @Override
            @NonNull
            public HttpStatus getStatusCode() {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            // 定义熔断后的响应状态码
            @Override
            public int getRawStatusCode() {
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            // 定义熔断后的响应状态文本
            @Override
            @NonNull
            public String getStatusText() {
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {
            }
        };
    }
}

