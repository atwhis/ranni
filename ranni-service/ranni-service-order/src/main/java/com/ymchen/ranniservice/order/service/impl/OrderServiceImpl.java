package com.ymchen.ranniservice.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.rannibase.entity.order.Order;
import com.ymchen.ranniservice.order.mapper.OrderMapper;
import com.ymchen.ranniservice.order.remote.UserRemoteService;
import com.ymchen.ranniservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final UserRemoteService userRemoteService;

    @Override
    public Order getById(Long orderId) {
        return baseMapper.selectById(orderId);
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        QueryWrapper<Order> query = new QueryWrapper<Order>();
        query.eq("order_user", userId);
        return baseMapper.selectList(query);
    }

    @Transactional
    @Override
    public void createOrder(Long userId) {
        Order order = new Order();
        order.setOrderState(1).setOrderUser(userId).setRemark("remark for test").setOrderNo(UUID.randomUUID().toString().substring(0, 5));
        int insert = baseMapper.insert(order);
    }

    @Override
    public OrderDTO getOrderDetail(Long orderId) {
        Order order = baseMapper.selectById(orderId);
        User user = userRemoteService.getById(order.getId().intValue());
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setUserName(user.getUserName());
        return orderDTO;
    }
}
