package com.ms.zg.book.mock.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    boolean isVIP(String userId) {
        return userId.equals("1");
    }
}
