package com.ms.zg.book.securityreactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@EnableWebFluxSecurity
@EnableRedisWebSession
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .authorizeExchange().pathMatchers("/actuator/health", "/login").permitAll()
                .and().authorizeExchange().pathMatchers("/users/**").hasAuthority("admin")
                .and().authorizeExchange().pathMatchers("/goods/**").authenticated()
                .and().httpBasic()
                .and().logout()
                .and().build();
    }

    // 定义响应式的用户查询服务
    /*
    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> {
            if (!username.equals("zhanggang")) {
                throw new UsernameNotFoundException(username + " not found");
            }
            // 使用默认的PasswordEncoder进行密码加密，实际项目中根据具体情况进行密码校验
            final PasswordEncoder passwordEncoder =
                    PasswordEncoderFactories.createDelegatingPasswordEncoder();
            final String password = passwordEncoder.encode("123456");
            final User user = User.builder().id("1").username(username).password(password)
                    .roles(Collections.singleton("admin")).build();
            return Mono.just(user);
        };
    }
    */

}

