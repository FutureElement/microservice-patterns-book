package com.ms.zg.book.webfluxbff.service;

import com.ms.zg.book.webfluxbff.model.LogisticsRecord;
import com.ms.zg.book.webfluxbff.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderDetailService {
    private final WebClient.Builder ordersWebClientBuilder;
    private final WebClient.Builder logisticsWebClientBuilder;

    @Autowired
    public OrderDetailService(WebClient.Builder ordersWebClientBuilder,
                              WebClient.Builder logisticsWebClientBuilder) {
        this.ordersWebClientBuilder = ordersWebClientBuilder;
        this.logisticsWebClientBuilder = logisticsWebClientBuilder;
    }

    public Mono<OrderDetail> findOrderDetails(String orderId) {
        final Mono<OrderDetail> orderVoMono = ordersWebClientBuilder.build()
                .get().uri("/orders/{id}", orderId).retrieve()
                .bodyToMono(OrderDetail.class);
        final Flux<LogisticsRecord> logisticsRecordFlux = logisticsWebClientBuilder.build()
                .get().uri("/logistics/records?orderId={id}", orderId).retrieve()
                .bodyToFlux(LogisticsRecord.class);
        return Mono.zip(orderVoMono, logisticsRecordFlux.collectList()).map(t -> {
            t.getT1().setLogisticsRecordList(t.getT2());
            return t.getT1();
        });
    }
}

