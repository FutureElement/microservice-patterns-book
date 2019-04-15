package com.ms.zg.book.consumer.repository;

import com.ms.zg.book.consumer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository {

    private static final String PROVIDER_BASE_URL = "http://ms-provider";
    private final RestTemplate restTemplate;
    private final WebClient.Builder loadBalancedWebClientBuilder;

    @Autowired
    public UserRepository(RestTemplate restTemplate, WebClient.Builder loadBalancedWebClientBuilder) {
        this.restTemplate = restTemplate;
        this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder;
    }

    public User getUserById(String id) {
        return restTemplate.getForObject(PROVIDER_BASE_URL + "/users/{0}", User.class, id);
    }

    public Mono<User> getMonoUserById(String id) {
        return loadBalancedWebClientBuilder.baseUrl(PROVIDER_BASE_URL).build().get().uri("/users/{0}", id)
                .retrieve().bodyToMono(User.class);
    }

}
