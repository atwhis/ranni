package com.ymchen.ranniservice.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.rannibase.entity.order.Order;

import java.util.List;

public interface OrderService extends IService<Order> {

    public Order getById(Long orderId);

    public List<Order> getOrdersByUser(Long userId);

    public void createOrder(Long userId);

    public OrderDTO getOrderDetail(Long orderId);
}
