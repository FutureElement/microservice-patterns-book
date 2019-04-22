package com.ms.zg.book.graphql.service;

import com.ms.zg.book.graphql.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {
    public Role[] findRolesByUserId(String userId) {
        if (userId.equals("1")) {
            return new Role[]{new Role(1, "admin")};
        }
        return new Role[0];
    }

    public List<Role[]> findRolesByUserIds(List<String> keys) {
        return keys.stream().map(userId -> {
            if (userId.equals("1")) {
                return new Role[]{new Role(1, "admin")};
            }
            return new Role[0];
        }).collect(Collectors.toList());
    }
}
