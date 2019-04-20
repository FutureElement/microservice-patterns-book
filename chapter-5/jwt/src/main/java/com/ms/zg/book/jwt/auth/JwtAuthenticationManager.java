package com.ms.zg.book.jwt.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.zg.book.jwt.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // principal 即为jwt文本信息
        String jwt = (String) authentication.getPrincipal();
        if (jwt == null) {
            return null;
        }
        // 使用JWTVerifier校验jwt
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        final DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
        final User user = User.builder().id(decodedJWT.getClaim("userId").asString())
                .username(decodedJWT.getClaim("username").asString())
                .roles(Arrays.stream(decodedJWT.getClaim("roles").asArray(String.class))
                        .collect(Collectors.toSet()))
                .build();
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationToken.setAuthenticated(true);
        return Mono.just(authenticationToken);
    }

    public String createJwtBasicToken(User user) {
        final HashMap<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, 30);
        Date expire = calendar.getTime();
        // 创建JWT
        String jwt = JWT.create().withHeader(headers)
                .withClaim("userId", user.getId())
                .withClaim("username", user.getUsername())
                .withArrayClaim("roles", user.getRoles().toArray(new String[0]))
                .withIssuedAt(now)
                .withExpiresAt(expire)
                .sign(Algorithm.HMAC256(secret));
        // 构建JWT的token
        final String token = jwt + ":";
        return Base64Utils.encodeToUrlSafeString(token.getBytes());
    }
}

