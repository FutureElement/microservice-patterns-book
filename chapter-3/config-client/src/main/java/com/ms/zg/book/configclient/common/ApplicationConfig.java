package com.ms.zg.book.configclient.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {
    @Value("${app.name}")
    private String appName;

    public String getAppName() {
        return appName;
    }
}
