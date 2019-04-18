package com.ms.zg.book.pactprovider.user;

import com.ms.zg.book.pactprovider.model.Role;
import com.ms.zg.book.pactprovider.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User findById(String id) {
        return new User(id, "zhanggang", new Role[]{new Role(1, "admin")});
    }
}
