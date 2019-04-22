package com.ms.zg.book.graphql.service;

import com.ms.zg.book.graphql.model.Gender;
import com.ms.zg.book.graphql.model.Role;
import com.ms.zg.book.graphql.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    public User findById(String id) {
        final User user = new User();
        user.setId(id);
        user.setUsername("zhanggang");
        user.setAge(18);
        user.setGender(Gender.MALE);
        user.setRoles(new Role[]{new Role(1, "admin")});
        return user;
    }


    public List<User> findByIds(List<String> ids) {
        return ids.stream().map(this::findById).collect(Collectors.toList());
    }
}
