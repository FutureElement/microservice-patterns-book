package com.ms.zg.book.jwt.controller;

import com.ms.zg.book.jwt.auth.JwtAuthenticationManager;
import com.ms.zg.book.jwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
public class LoginController {
    private final JwtAuthenticationManager jwtAuthenticationManager;

    @Autowired
    public LoginController(JwtAuthenticationManager jwtAuthenticationManager) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
    }

    @NonNull
    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UsernamePassword.class)
                .map(usernamePassword ->
                        new UsernamePasswordAuthenticationToken(usernamePassword.username,
                                usernamePassword.password))
                .flatMap(this::authenticate)
                .doOnError(e -> status(HttpStatus.UNAUTHORIZED).build())
                .map(jwtAuthenticationManager::createJwtBasicToken)
                .flatMap(token -> ok().syncBody(token));
    }

    private Mono<User> authenticate(Authentication authentication) {
        if (!authentication.getPrincipal().equals("zhanggang")
                || !authentication.getCredentials().equals("123456")) {
            throw new BadCredentialsException("Error username or password");
        }
        final User user = User.builder().id("1").username("zhanggang")
                .roles(Collections.singleton("admin")).build();
        return Mono.just(user);
    }
}

