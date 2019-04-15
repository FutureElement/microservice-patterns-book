package com.ms.zg.book.consumer.repository;

import com.ms.zg.book.consumer.ConsumerApplicationTests;
import com.ms.zg.book.consumer.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 需要启动注册中心和provider
 */
public class UserRepositoryTest extends ConsumerApplicationTests {

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