package com.ms.zg.book.circuitbreaker.annotation;

import com.ms.zg.book.circuitbreaker.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.stereotype.Service;

@Service
public class UserService2 {

    private static int count = 0;

    public UserService2() {
        cleanCount();
    }

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "findUserById")
    public User findUserById(String id) {
        increment();
        System.out.println("调用了findUserById");
        return new User(id);
    }

    @CacheRemove(commandKey = "findUserById", cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "updateUser")
    public User updateUser(User user) {
        System.out.println("调用了updateUser");
        return user;
    }


    public String getCacheKey(String id) {
        return "user_id_" + id;
    }

    public String getCacheKey(User user) {
        return getCacheKey(user.getId());
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
