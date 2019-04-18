package com.ms.zg.book.pactprovider;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import com.ms.zg.book.pactprovider.controller.UserController;
import com.ms.zg.book.pactprovider.model.Role;
import com.ms.zg.book.pactprovider.model.User;
import com.ms.zg.book.pactprovider.user.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//不使用SpringRestPactRunner，而使用简单的PactRunner启动测试
@RunWith(PactRunner.class)
@Provider("user-service")
@PactBroker(host = "localhost", port = "9500", consumers = "order-service")
public class UserServicePactContractTest4 {
    //使用MockMvcTarget配置服务端
    @TestTarget
    public final MockMvcTarget target = new MockMvcTarget();
    private final UserService userService = mock(UserService.class);

    @Before
    public void before() {
        //创建用户Controller
        final UserController userController = new UserController(userService);
        //将UserController注入target
        target.setControllers(userController);
    }

    //声明state，测试契约
    @State("Dsl test GET /user/{id}")
    public void test1() {
        when(userService.findById("1")).thenReturn(
                new User("1", "zhanggang", new Role[]{new Role(1, "admin")})
        );
    }
}


