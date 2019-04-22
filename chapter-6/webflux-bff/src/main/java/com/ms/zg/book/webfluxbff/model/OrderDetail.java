package com.ms.zg.book.webfluxbff.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderDetail {
    private String id;
    private double amount;
    private String goodId;
    private List<LogisticsRecord> logisticsRecordList;
}
