package com.ms.zg.book.graphql;

import com.ms.zg.book.graphql.controller.GraphQLWebFluxController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(GraphQLWebFluxController graphQLController) {
        return route(POST("/graphql2"), graphQLController::query);
    }

    @Bean
    public WebClient usersWebClient(@Value("${services.users.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

}
