package com.ms.zg.book.circuitbreaker.service;

import com.ms.zg.book.circuitbreaker.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    private static int count = 0;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        cleanCount();
    }

    @HystrixCommand(fallbackMethod = "findUserNameByIdFallback")
    public String findUserNameById(String id) {
        return restTemplate.getForObject("/users/{id}/name", String.class, id);
    }

    @SuppressWarnings("unused")
    public String findUserNameByIdFallback(String id) {
        return "ZhangGang";
    }


    public List<User> findUserByIds(List<String> ids) {
        increment();
        return ids.stream().map(id -> new User(id, "test")).collect(Collectors.toList());
    }

    private static void increment() {
        count++;
    }

    private static void cleanCount() {
        count = 0;
    }

    public int getCounts() {
        return count;
    }
}
