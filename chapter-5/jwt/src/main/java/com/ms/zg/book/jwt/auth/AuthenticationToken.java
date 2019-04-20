package com.ms.zg.book.jwt.auth;

import com.ms.zg.book.jwt.model.User;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
public class AuthenticationToken extends AbstractAuthenticationToken {
    private final User user;
    private final String credentials;

    public AuthenticationToken(User user) {
        super(user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.user = user;
        this.credentials = UUID.randomUUID().toString();
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}



