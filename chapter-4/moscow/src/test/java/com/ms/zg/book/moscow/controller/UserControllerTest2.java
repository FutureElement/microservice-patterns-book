package com.ms.zg.book.moscow.controller;


import com.ms.zg.book.moscow.ContractTestBase;
import com.ms.zg.book.moscow.model.Role;
import com.ms.zg.book.moscow.model.User;
import com.ms.zg.book.moscow.service.UserService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

public class UserControllerTest2 extends ContractTestBase {
    @MockBean
    private UserService userService;

    @Test
    public void find_user_by_id() {
        when(userService.findById("1")).thenReturn(
                new User("1", "zhanggang", new Role[]{new Role(1, "admin")})
        );
        //使用当前方法的名称作为findContracts的入参
        assertContract();
    }
}
