package com.ms.zg.book.graphql.controller;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GraphqlController {
    private final GraphQL graphQL;
    private final DataLoaderRegistry dataLoaderRegistry;

    @Autowired
    public GraphqlController(GraphQL graphQL, DataLoaderRegistry dataLoaderRegistry) {
        this.graphQL = graphQL;
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    @PostMapping("/graphql")
    public Map<String, Object> execute(@RequestBody GraphqlRequest graphqlRequest) {
        final ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(graphqlRequest.getQuery())
                .dataLoaderRegistry(dataLoaderRegistry)
                .variables(graphqlRequest.getVariables())
                .build();
        final ExecutionResult result = graphQL.execute(executionInput);
        return result.toSpecification();
    }
}

