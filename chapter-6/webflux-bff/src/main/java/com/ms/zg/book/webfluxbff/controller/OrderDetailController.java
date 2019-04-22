package com.ms.zg.book.webfluxbff.controller;

import com.ms.zg.book.webfluxbff.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @NonNull
    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable("id");
        return orderDetailService.findOrderDetails(id)
                .flatMap(orderDetail -> ok().syncBody(orderDetail));
    }
}

