package com.ms.zg.book.security.controller;

import com.ms.zg.book.security.authenticate.UserAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

//登录接口
@RestController
@RequestMapping
public class LoginController {
    private final UserAuthenticationManager userAuthenticationManager;

    //构造器注入用户认证管理器
    @Autowired
    public LoginController(UserAuthenticationManager userAuthenticationManager) {
        this.userAuthenticationManager = userAuthenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsernamePassword usernamePassword) {
        // new一个UsernamePasswordAuthenticationToken
        final UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(usernamePassword.username,
                usernamePassword.password);
        // 使用用户认证管理器进行用户认证
        final Authentication authentication = userAuthenticationManager.authenticate(token);
        // 将认证结果保存到Security上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return status(HttpStatus.NO_CONTENT).build();
    }

}

