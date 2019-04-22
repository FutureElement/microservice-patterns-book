package com.ms.zg.book.graphql.query;

import com.ms.zg.book.graphql.model.User;
import com.ms.zg.book.graphql.service.UserService;
import com.ms.zg.book.graphql.service.WebFluxUserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UserQueryDataFetcher implements DataFetcher<CompletableFuture<User>> {
    private final UserService userService;
    private final WebFluxUserService webFluxUserService;

    @Autowired
    public UserQueryDataFetcher(UserService userService, WebFluxUserService webFluxUserService) {
        this.userService = userService;
        this.webFluxUserService = webFluxUserService;
    }

    @Override
    public CompletableFuture<User> get(DataFetchingEnvironment environment) {
        /*return CompletableFuture.supplyAsync(() -> {
            final String id = environment.getArgument("id");
            return userService.findById(id);
        });*/
        final String id = environment.getArgument("id");
        return webFluxUserService.findById(id).toFuture();
    }
}

