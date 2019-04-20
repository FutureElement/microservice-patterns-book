package com.ms.zg.book.security.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableWebSecurity
@Configurable
@EnableRedisHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //配置相应路径的权限规则
                .antMatchers("/login").permitAll()
                .antMatchers("/users/**").hasAuthority("admin")
                .antMatchers("/goods/**").authenticated()
                .anyRequest().authenticated();
    }
}
