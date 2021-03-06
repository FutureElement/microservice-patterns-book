package com.ms.zg.book.consumer.repository;

import com.ms.zg.book.consumer.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 需要启动注册中心和provider
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserById() {
        final User user = userRepository.getUserById("1");

        assert user != null;
        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("demo");
    }

    @Test
    public void getMonoUserById() {
        final Mono<User> monoUser = userRepository.getMonoUserById("2");
        final User user = monoUser.block();

        assert user != null;
        assertThat(user.getId()).isEqualTo("2");
        assertThat(user.getName()).isEqualTo("demo");
    }
}