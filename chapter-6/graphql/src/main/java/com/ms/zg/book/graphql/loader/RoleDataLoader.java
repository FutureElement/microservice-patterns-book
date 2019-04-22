package com.ms.zg.book.graphql.loader;

import com.ms.zg.book.graphql.model.Role;
import com.ms.zg.book.graphql.service.UserRoleService;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class RoleDataLoader extends DataLoader<String, Role[]> {
    @Autowired
    public RoleDataLoader(UserRoleService userRoleService) {
        super(keys -> CompletableFuture.supplyAsync(() ->
                userRoleService.findRolesByUserIds(keys)
        ));
    }
}

