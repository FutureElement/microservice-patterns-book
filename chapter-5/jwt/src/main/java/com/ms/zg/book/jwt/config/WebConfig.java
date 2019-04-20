package com.ms.zg.book.jwt.config;

import com.ms.zg.book.jwt.controller.LoginController;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configurable
@Component
public class WebConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(LoginController loginController) {
        return route(POST("/login"), loginController::login);
    }
}

