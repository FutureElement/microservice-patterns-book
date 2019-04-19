package com.ms.zg.book.springcloudcontractconsumer.repository;

import com.ms.zg.book.springcloudcontractconsumer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class UserClient {

    private final RestTemplate restTemplate;

    private final String userServiceUrl;

    @Autowired
    public UserClient(RestTemplate restTemplate, @Value(("${user-service.url}")) String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    //定义findUserById方法
    public User findUserById(String id) {
        return restTemplate.getForObject(userServiceUrl + "/users/{0}", User.class, id);
    }

}
