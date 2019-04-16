package com.ms.zg.book.circuitbreaker.annotation;

import com.ms.zg.book.circuitbreaker.SpringTestBase;
import com.ms.zg.book.circuitbreaker.model.User;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserService2Test extends SpringTestBase {
    @Autowired
    private UserService2 userService2;

    private HystrixRequestContext context;

    @Before
    public void before() {
        // 初始化Hystrix
        context = HystrixRequestContext.initializeContext();
        HystrixRequestContext.setContextOnCurrentThread(context);
    }

    @After
    public void after() {
        context.shutdown();
    }

    @Test
    public void testAnnotation() {
        //第一次查询调用真实服务
        User user = userService2.findUserById("1");
        assertThat(user.getId()).isEqualTo("1");
        //再次查询缓存命中
        User user2 = userService2.findUserById("1");
        assertThat(user2.getId()).isEqualTo("1");

        assertThat(userService2.getCounts()).isEqualTo(1);
        HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("findUserById"),
                HystrixPlugins.getInstance().getConcurrencyStrategy()).clear("user_id_1");
        //更新后清除缓存
        userService2.updateUser(new User("1"));
        //更新后再次调用真实服务
        User user3 = userService2.findUserById("1");
        assertThat(user3.getId()).isEqualTo("1");

        assertThat(userService2.getCounts()).isEqualTo(2);
    }

}