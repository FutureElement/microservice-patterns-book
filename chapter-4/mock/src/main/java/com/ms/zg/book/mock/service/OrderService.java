package com.ms.zg.book.mock.service;

import com.ms.zg.book.mock.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//订单服务
@Service
public class OrderService {
    //声明一个用户服务
    private final UserService userService;

    //通过构造器注入用户服务
    @Autowired
    public OrderService(UserService userService) {
        this.userService = userService;
    }

    //获取订单的最终价格
    public double getPrice(Order order) {
        //获取订单的原始价格
        double originalPrice = order.getPrice();
        //根据用户id判断用户是否是VIP
        final boolean vipUser = userService.isVIP(order.getUserId());
        //计算最终价格
        if (vipUser) {
            return originalPrice * 0.9;
        }
        return originalPrice;
    }
}

