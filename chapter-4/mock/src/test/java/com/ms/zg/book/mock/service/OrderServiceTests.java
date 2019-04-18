package com.ms.zg.book.mock.service;

import com.ms.zg.book.mock.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//spring boot测试注解
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {
    //注入订单服务
    @Autowired
    private OrderService orderService;

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

    //测试非VIP用户的订单价格
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
