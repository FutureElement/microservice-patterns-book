package com.ms.zg.book.circuitbreaker.feign;

import com.ms.zg.book.circuitbreaker.SpringTestBase;
import com.ms.zg.book.circuitbreaker.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFeignClientTest extends SpringTestBase {

    @Autowired
    private UserFeignClient userFeignClient;

    @Test
    public void getUserById() {
        final User user = userFeignClient.getUserById("1");

        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("ZhangGang");
    }

}