package com.ms.zg.book.graphql.query;

import com.ms.zg.book.graphql.model.User;
import com.ms.zg.book.graphql.service.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserListQueryDataFetcher implements DataFetcher<List<User>> {
    private final UserService userService;

    @Autowired
    public UserListQueryDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> get(DataFetchingEnvironment environment) {
        final List<String> ids = environment.getArgument("ids");
        return userService.findByIds(ids);
    }
}

