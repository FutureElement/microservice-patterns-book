package com.ms.zg.book.consumer.service;

import com.ms.zg.book.consumer.model.User;
import com.ms.zg.book.consumer.repository.UserFeignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserFeignRepository userFeignRepository;

    @Autowired
    public UserService(UserFeignRepository userFeignRepository) {
        this.userFeignRepository = userFeignRepository;
    }

    public User getUserById(String id) {
        return userFeignRepository.getUserById(id);
    }

}
