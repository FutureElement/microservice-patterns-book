package com.ms.zg.book.circuitbreaker.command;

import com.ms.zg.book.circuitbreaker.model.User;
import com.ms.zg.book.circuitbreaker.service.UserService;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandCollapserGetUserByIdTest {

    private HystrixRequestContext context;

    @Before
    public void before() {
        // 初始化Hystrix
        context = HystrixRequestContext.initializeContext();
        HystrixRequestContext.setContextOnCurrentThread(context);
    }

    @After
    public void after() {
        context.close();
    }

    @Test
    public void testOneCallUserService() throws ExecutionException, InterruptedException {
        final UserService userService = new UserService(null);
        //使用CommandCollapserGetUserById模拟5次用户查询的请求
        Future<User> f1 = new CommandCollapserGetUserById("1", userService).queue();
        Future<User> f2 = new CommandCollapserGetUserById("2", userService).queue();
        Future<User> f3 = new CommandCollapserGetUserById("3", userService).queue();
        Future<User> f4 = new CommandCollapserGetUserById("4", userService).queue();
        Future<User> f5 = new CommandCollapserGetUserById("5", userService).queue();

        //输出5次请求的结果
        assertThat(f1.get().getId()).isEqualTo("1");
        assertThat(f2.get().getId()).isEqualTo("2");
        assertThat(f3.get().getId()).isEqualTo("3");
        assertThat(f4.get().getId()).isEqualTo("4");
        assertThat(f5.get().getId()).isEqualTo("5");

        //验证UserService只被调用了一次
        assertThat(userService.getCounts()).isEqualTo(1);
    }

    @Test
    public void testTwoCallUserService() throws ExecutionException, InterruptedException {
        final UserService userService = new UserService(null);
        //使用CommandCollapserGetUserById模拟5次用户查询的请求
        Future<User> f1 = new CommandCollapserGetUserById("1", userService).queue();
        Future<User> f2 = new CommandCollapserGetUserById("2", userService).queue();
        Future<User> f3 = new CommandCollapserGetUserById("3", userService).queue();
        //延迟20ms
        Thread.sleep(200);

        Future<User> f4 = new CommandCollapserGetUserById("4", userService).queue();
        Future<User> f5 = new CommandCollapserGetUserById("5", userService).queue();

        //输出5次请求的结果
        assertThat(f1.get().getId()).isEqualTo("1");
        assertThat(f2.get().getId()).isEqualTo("2");
        assertThat(f3.get().getId()).isEqualTo("3");
        assertThat(f4.get().getId()).isEqualTo("4");
        assertThat(f5.get().getId()).isEqualTo("5");

        //验证UserService被调用了2次
        assertThat(userService.getCounts()).isEqualTo(2);
    }

    @Test
    public void testCallUserServiceByCache() throws ExecutionException, InterruptedException {
        final UserService userService = new UserService(null);
        //使用CommandCollapserGetUserById模拟5次用户查询的请求
        Future<User> f1 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f2 = new CommandCollapserGetUserById2("2", userService).queue();
        //延迟20ms
        Thread.sleep(200);

        Future<User> f3 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f4 = new CommandCollapserGetUserById2("2", userService).queue();

        //输出5次请求的结果
        assertThat(f1.get().getId()).isEqualTo("1");
        assertThat(f2.get().getId()).isEqualTo("2");
        assertThat(f3.get().getId()).isEqualTo("1");
        assertThat(f4.get().getId()).isEqualTo("2");

        //由于缓存, UserService被调用了1次
        assertThat(userService.getCounts()).isEqualTo(1);
    }

    @Test
    public void testCallUserServiceByNotCleanCache() throws ExecutionException, InterruptedException {
        final UserService userService = new UserService(null);
        //使用CommandCollapserGetUserById模拟5次用户查询的请求
        Future<User> f1 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f2 = new CommandCollapserGetUserById2("2", userService).queue();
        //延迟20ms
        Thread.sleep(20);

        Future<User> f3 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f4 = new CommandCollapserGetUserById2("2", userService).queue();

        //输出5次请求的结果
        assertThat(f1.get().getId()).isEqualTo("1");
        assertThat(f2.get().getId()).isEqualTo("2");
        assertThat(f3.get().getId()).isEqualTo("1");
        assertThat(f4.get().getId()).isEqualTo("2");

        //增加f5，f6次请求
        Future<User> f5 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f6 = new CommandCollapserGetUserById2("2", userService).queue();

        assertThat(f5.get().getId()).isEqualTo("1");
        assertThat(f6.get().getId()).isEqualTo("2");

        //由于缓存, UserService被调用了1次
        assertThat(userService.getCounts()).isEqualTo(1);
    }

    @Test
    public void testCallUserServiceByCleanCache() throws ExecutionException, InterruptedException {
        final UserService userService = new UserService(null);
        //使用CommandCollapserGetUserById模拟5次用户查询的请求
        Future<User> f1 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f2 = new CommandCollapserGetUserById2("2", userService).queue();
        //延迟20ms
        Thread.sleep(20);

        Future<User> f3 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f4 = new CommandCollapserGetUserById2("2", userService).queue();

        //输出5次请求的结果
        assertThat(f1.get().getId()).isEqualTo("1");
        assertThat(f2.get().getId()).isEqualTo("2");
        assertThat(f3.get().getId()).isEqualTo("1");
        assertThat(f4.get().getId()).isEqualTo("2");

        //增加f5，f6次请求
        Future<User> f5 = new CommandCollapserGetUserById2("1", userService).queue();
        Future<User> f6 = new CommandCollapserGetUserById2("2", userService).queue();
        //调用HystrixRequestCache的clear方法清除缓存
        final String cacheKey = "get_user_by_ids_" + Arrays.toString(new String[]{"1", "2"});

        HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("GetUserById"),
                HystrixPlugins.getInstance().getConcurrencyStrategy()).clear(cacheKey);

        assertThat(f5.get().getId()).isEqualTo("1");
        assertThat(f6.get().getId()).isEqualTo("2");

        //UserService被调用了2次
        assertThat(userService.getCounts()).isEqualTo(2);
    }

}