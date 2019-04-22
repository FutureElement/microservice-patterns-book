package com.ms.zg.book.graphql.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class GraphqlRequest {
    private String query;
    private Map<String, Object> variables;
}
