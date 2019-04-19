package com.ms.zg.book.moscow.controller;


import com.github.macdao.moscow.ContractAssertion;
import com.github.macdao.moscow.ContractContainer;
import com.ms.zg.book.moscow.model.Role;
import com.ms.zg.book.moscow.model.User;
import com.ms.zg.book.moscow.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
//指定web环境使用随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//自动配置TestClient
@AutoConfigureWebTestClient
public class UserControllerTest {
    //获取本地服务端口号
    @LocalServerPort
    private int port;
    //mock用户服务
    @MockBean
    private UserService userService;
    //加载moscow契约文件
    private final ContractContainer contractContainer =
            new ContractContainer(Paths.get("src/test/resources/moscow"));

    //定义用户服务行为
    @Before
    public void before() {
        when(userService.findById("1")).thenReturn(
                new User("1", "zhanggang", new Role[]{new Role(1, "admin")})
        );
    }

    //测试契约
    @Test
    public void assertContract() {
        //find_user_by_id与契约文件中的单个契约的description保持一致
        new ContractAssertion(contractContainer.findContracts("find_user_by_id"))
                .setPort(port)
                .assertContract();
    }

}