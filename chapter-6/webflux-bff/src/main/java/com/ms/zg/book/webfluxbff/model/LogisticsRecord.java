package com.ms.zg.book.webfluxbff.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LogisticsRecord {
    private String id;
    private LocalDateTime createTime;
    private String address;
}
