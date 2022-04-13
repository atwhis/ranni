package com.ymchen.ranniservice.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymchen.ranniservice.order.entity.Order;

public interface OrderService extends IService<Order> {

    public Order getById(Long orderId);
}
