package com.ms.zg.book.provider;

import com.ms.zg.book.provider.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUserById(String id) {
        final User user = new User();
        user.setId(id);
        user.setName("demo");
        return user;
    }
}
