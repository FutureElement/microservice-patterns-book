package com.ms.zg.book.springcloudcontractprovider.controller;

import com.ms.zg.book.springcloudcontractprovider.model.User;
import com.ms.zg.book.springcloudcontractprovider.service.UserService;
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

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.findById(id);
    }
}
