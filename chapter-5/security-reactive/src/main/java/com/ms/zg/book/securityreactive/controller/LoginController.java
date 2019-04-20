package com.ms.zg.book.securityreactive.controller;

import com.ms.zg.book.securityreactive.authnticate.AuthenticationToken;
import com.ms.zg.book.securityreactive.model.User;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
public class LoginController {
    @NonNull
    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        // 通过ServerRequest获取用户登录输入的用户名密码
        return serverRequest.bodyToMono(UsernamePassword.class)
                .map(usernamePassword ->
                        new UsernamePasswordAuthenticationToken(usernamePassword.username,
                                usernamePassword.password))
                // 执行认证
                .flatMap(this::authenticate)
                .doOnError(e -> status(HttpStatus.UNAUTHORIZED).build())
                // 传入session
                .zipWith(serverRequest.session())
                .map(t -> {
                    // 将认证结果保存到session中
                    t.getT2().getAttributes().put("authentication", t.getT1());
                    return createToken(t.getT2().getId(), (String) t.getT1().getCredentials());
                }).flatMap(token -> ok().syncBody(token));
    }

    private String createToken(String sessionId, String credentials) {
        final String token = sessionId + ":" + credentials;
        return Base64Utils.encodeToString(token.getBytes());
    }

    private Mono<Authentication> authenticate(Authentication authentication) {
        if (!authentication.getPrincipal().equals("zhanggang")
                || !authentication.getCredentials().equals("123456")) {
            throw new BadCredentialsException("Error username or password");
        }
        final User user = User.builder().id("1").username("zhanggang")
                .roles(Collections.singleton("admin")).build();
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationToken.setAuthenticated(true);
        return Mono.just(authenticationToken);
    }

}

