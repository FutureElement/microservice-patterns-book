package com.ms.zg.bool.springcloudgateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersFallbackController {
    @RequestMapping("/fallback")
    public String fallback() {
        return "users service crashed";
    }
}

