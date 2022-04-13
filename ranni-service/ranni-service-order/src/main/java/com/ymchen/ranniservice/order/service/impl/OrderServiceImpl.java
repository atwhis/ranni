package com.ymchen.ranniservice.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymchen.ranniservice.order.entity.Order;
import com.ymchen.ranniservice.order.mapper.OrderMapper;
import com.ymchen.ranniservice.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Order getById(Long orderId) {
        return baseMapper.selectById(orderId);
    }
}
