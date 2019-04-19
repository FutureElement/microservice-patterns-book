package com.ms.zg.book.springcloudcontractconsumer.repository;

import com.ms.zg.book.springcloudcontractconsumer.model.Role;
import com.ms.zg.book.springcloudcontractconsumer.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
//指定Mock服务的启动端口号
@AutoConfigureWireMock(port = 8081)
public class UserClientTest {
    //自动注入UserClient
    @Autowired
    private UserClient userClient;

    //测试findUserById
    @Test
    public void find_user_by_id_happy_path() {
        //given
        final String userId = "1";
        //when
        final User user = userClient.findUserById(userId);
        //then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("zhanggang");
        assertThat(user.getRoles()).extracting(Role::getName).contains("admin");
    }

}
