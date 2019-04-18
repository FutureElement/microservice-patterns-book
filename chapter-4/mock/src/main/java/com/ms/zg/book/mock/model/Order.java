package com.ms.zg.book.mock.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {
    private final String id;
    private final String userId;
    private final double price;
}
