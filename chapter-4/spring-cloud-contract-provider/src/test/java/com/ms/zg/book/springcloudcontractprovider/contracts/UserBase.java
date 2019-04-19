package com.ms.zg.book.springcloudcontractprovider.contracts;

import com.ms.zg.book.springcloudcontractprovider.controller.UserController;
import com.ms.zg.book.springcloudcontractprovider.model.Role;
import com.ms.zg.book.springcloudcontractprovider.model.User;
import com.ms.zg.book.springcloudcontractprovider.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserBase {
    @Before
    public void setup() {
        //mock用户服务
        final UserService userService = mock(UserService.class);
        //声明用户服务的findById行为
        when(userService.findById("1")).thenReturn(
                new User("1", "zhanggang", new Role[]{new Role(1, "admin")})
        );
        //加载UserController
        RestAssuredMockMvc.standaloneSetup(new UserController(userService));
    }

}
