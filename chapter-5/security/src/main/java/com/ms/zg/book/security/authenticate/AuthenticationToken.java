package com.ms.zg.book.security.authenticate;

import com.ms.zg.book.security.model.User;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

// 自定义token类
@EqualsAndHashCode(callSuper = true)
public class AuthenticationToken extends AbstractAuthenticationToken {
    private final User user;

    AuthenticationToken(User user) {
        super(user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}

