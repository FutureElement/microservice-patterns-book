package com.ms.zg.book.consumer.service;

import com.ms.zg.book.consumer.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 需要启动注册中心和provider
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserById() {
        final User user = userService.getUserById("3");

        assert user != null;
        assertThat(user.getId()).isEqualTo("3");
        assertThat(user.getName()).isEqualTo("demo");
    }
}