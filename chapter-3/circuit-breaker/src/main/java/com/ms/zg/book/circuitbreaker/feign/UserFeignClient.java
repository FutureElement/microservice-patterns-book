package com.ms.zg.book.circuitbreaker.feign;

import com.ms.zg.book.circuitbreaker.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-provider", fallbackFactory = UserFeignClientFallBack.class)
public interface UserFeignClient {
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") String id);
}

