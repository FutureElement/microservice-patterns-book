package com.ms.zg.book.pactprovider.controller;

import com.ms.zg.book.pactprovider.model.User;
import com.ms.zg.book.pactprovider.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }
}
