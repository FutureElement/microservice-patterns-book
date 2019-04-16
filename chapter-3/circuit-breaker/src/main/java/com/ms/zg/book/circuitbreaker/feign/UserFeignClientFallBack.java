package com.ms.zg.book.circuitbreaker.feign;

import com.ms.zg.book.circuitbreaker.model.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallBack implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable cause) {
        return id -> new User(id, "ZhangGang");
    }
}

