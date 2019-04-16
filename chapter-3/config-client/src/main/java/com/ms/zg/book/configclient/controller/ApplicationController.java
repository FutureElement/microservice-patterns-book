package com.ms.zg.book.configclient.controller;

import com.ms.zg.book.configclient.common.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apps")
public class ApplicationController {
    private final ApplicationConfig applicationConfig;

    @Autowired
    public ApplicationController(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("name")
    public String getAppName() {
        return applicationConfig.getAppName();
    }
}
