package com.ms.zg.book.consumer.repository;

import com.ms.zg.book.consumer.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ms-provider")
public interface UserFeignRepository {
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") String id);
}
