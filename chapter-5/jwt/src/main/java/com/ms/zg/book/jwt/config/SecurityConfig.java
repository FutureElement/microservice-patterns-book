package com.ms.zg.book.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
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

}

