package com.ms.zg.book.security.authenticate;

import com.ms.zg.book.security.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = (String) authentication.getPrincipal();
        final String password = (String) authentication.getCredentials();
        // 校验用户名密码，这里实例代码，只实现简单的逻辑
        if (!username.equals("zhanggang") || !password.equals("123456")) {
            throw new BadCredentialsException("Error username or password");
        }
        final User user = User.builder()
                .id("1").username(username).roles(Collections.singleton("admin"))
                .build();
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        // 设置认证结果
        authenticationToken.setAuthenticated(true);
        return authenticationToken;
    }

}
