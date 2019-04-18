package com.ms.zg.book.pactprovider;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.ms.zg.book.pactprovider.model.Role;
import com.ms.zg.book.pactprovider.model.User;
import com.ms.zg.book.pactprovider.user.UserService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

//使用Pact提供的SpringRestPactRunner启动类
@RunWith(SpringRestPactRunner.class)
//定义服务提供者的名称
@Provider("user-service")
//契约文件路径
@PactFolder("PactContracts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServicePactContractTest2 {
    @MockBean
    private UserService userService;

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    //@State的值与JSON文件中providerStates的保持一致
    @State("Dsl test GET /user/{id}")
    public void test_get_user_by_id() {
            when(userService.findById("1")).thenReturn(
                    new User("1", "zhanggang", new Role[]{new Role(1, "admin")})
            );
    }
}

