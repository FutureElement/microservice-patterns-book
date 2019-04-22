package com.ms.zg.book.graphql.controller;

import graphql.ExecutionInput;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.fromFuture;

@Component
public class GraphQLWebFluxController {

    private final GraphQL graphQL;

    @Autowired
    public GraphQLWebFluxController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @Nullable
    public Mono<ServerResponse> query(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(GraphqlRequest.class)
                .map(graphqlVo -> ExecutionInput.newExecutionInput()
                        .query(graphqlVo.getQuery())
                        .variables(graphqlVo.getVariables())
                )
                .flatMap(input -> fromFuture(graphQL.executeAsync(input)))
                .flatMap(result -> ok().syncBody(result.toSpecification()))
                .switchIfEmpty(badRequest().build());
    }

}
