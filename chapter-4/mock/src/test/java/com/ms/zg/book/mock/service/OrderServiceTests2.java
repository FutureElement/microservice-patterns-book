package com.ms.zg.book.mock.service;

import com.ms.zg.book.mock.model.Order;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTests2 {

    //声明订单服务，但不使用自动注入
    private OrderService orderService;

    @Before
    public void before() {
        final UserService mockUserService = mock(UserService.class);
        when(mockUserService.isVIP("1")).thenReturn(true);
        when(mockUserService.isVIP("2")).thenReturn(false);
        //手动构建订单服务
        orderService = new OrderService(mockUserService);
    }

    //测试VIP用户的订单架构
    @Test
    public void when_get_price_by_vip_user_should_return_180() {
        //give
        final Order order = Order.builder().price(200).userId("1").build();
        //when
        final double price = orderService.getPrice(order);
        //then
        assertThat(price).isEqualTo(180);
    }

    //测试非VIP用户的订单架构
    @Test
    public void when_get_price_by_normal_user_should_return_200() {
        //give
        final Order order = Order.builder().price(200).userId("2").build();
        //when
        final double price = orderService.getPrice(order);
        //then
        assertThat(price).isEqualTo(200);
    }

}
