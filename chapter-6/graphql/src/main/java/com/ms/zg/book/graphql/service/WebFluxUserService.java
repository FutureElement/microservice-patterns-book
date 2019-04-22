package com.ms.zg.book.graphql.service;

import com.ms.zg.book.graphql.model.Gender;
import com.ms.zg.book.graphql.model.Role;
import com.ms.zg.book.graphql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebFluxUserService {
    private final WebClient usersWebClient;

    @Autowired
    public WebFluxUserService(WebClient usersWebClient) {
        this.usersWebClient = usersWebClient;
    }

    public Mono<User> findById(String id) {
//        return usersWebClient.get().uri("/users/{id}", id).retrieve().bodyToMono(User.class);

        final User user = new User();
        user.setId(id);
        user.setUsername("zhanggang");
        user.setAge(18);
        user.setGender(Gender.MALE);
        user.setRoles(new Role[]{new Role(1, "admin")});

        return Mono.just(user);
    }

}
