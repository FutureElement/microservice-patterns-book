package com.ms.zg.book.graphql.query;

import com.ms.zg.book.graphql.loader.RoleDataLoader;
import com.ms.zg.book.graphql.model.Role;
import com.ms.zg.book.graphql.model.User;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UserRoleQueryDataFetcher implements DataFetcher<CompletableFuture<Role[]>> {

    private final RoleDataLoader roleDataLoader;

    @Autowired
    public UserRoleQueryDataFetcher(RoleDataLoader roleDataLoader) {
        this.roleDataLoader = roleDataLoader;
    }

    @Override
    public CompletableFuture<Role[]> get(DataFetchingEnvironment environment) {
        User user = environment.getSource();
        final String userId = user.getId();
        return roleDataLoader.load(userId);
    }

}

