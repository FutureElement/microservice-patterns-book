package com.ms.zg.book.graphql.factory;

import com.ms.zg.book.graphql.loader.RoleDataLoader;
import com.ms.zg.book.graphql.query.UserListQueryDataFetcher;
import com.ms.zg.book.graphql.query.UserQueryDataFetcher;
import com.ms.zg.book.graphql.query.UserRoleQueryDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@Component
public class GraphQLFactory {
    @Value("classpath:api-schema.graphql")
    private Resource schema;

    private final UserQueryDataFetcher userQueryDataFetcher;
    private final UserRoleQueryDataFetcher userRoleQueryDataFetcher;
    private final UserListQueryDataFetcher userListQueryDataFetcher;
    private final RoleDataLoader roleDataLoader;

    @Autowired
    public GraphQLFactory(UserQueryDataFetcher userQueryDataFetcher,
                          UserRoleQueryDataFetcher userRoleQueryDataFetcher,
                          UserListQueryDataFetcher userListQueryDataFetcher,
                          RoleDataLoader roleDataLoader) {
        this.userQueryDataFetcher = userQueryDataFetcher;
        this.userRoleQueryDataFetcher = userRoleQueryDataFetcher;
        this.userListQueryDataFetcher = userListQueryDataFetcher;
        this.roleDataLoader = roleDataLoader;
    }

    @Bean
    public GraphQL graphQL() throws IOException {
        final File file = schema.getFile();
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(file);
        final RuntimeWiring.Builder runtimeWiringBuilder = newRuntimeWiring();

        runtimeWiringBuilder.type("Query", builder -> builder.dataFetcher("user", userQueryDataFetcher));
        runtimeWiringBuilder.type("User", builder -> builder.dataFetcher("roles", userRoleQueryDataFetcher));
        runtimeWiringBuilder.type("Query", builder -> builder.dataFetcher("userList", userListQueryDataFetcher));

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator
                .makeExecutableSchema(typeDefinitionRegistry, runtimeWiringBuilder.build());
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    @Bean
    public DataLoaderRegistry dataLoaderRegistry() {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register("roleDataLoader", roleDataLoader);
        return registry;
    }


}

