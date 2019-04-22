package com.ms.zg.book.webfluxbff.config;

import com.ms.zg.book.webfluxbff.controller.OrderDetailController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(OrderDetailController orderDetailController) {
        return route(GET("/orders-details/{id}"), orderDetailController::findById);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder ordersWebClientBuilder(@Value("${services.orders.url}") String url) {
        return WebClient.builder().baseUrl(url);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder logisticsWebClientBuilder(@Value("${services.logistics.url}") String url) {
        return WebClient.builder().baseUrl(url);
    }

}
