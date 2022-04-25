package com.ymchen.ranniservice.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymchen.rannibase.remote.UserRemoteService;
import com.ymchen.rannibase.util.SnowFlake;
import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.rannibase.entity.order.Order;
import com.ymchen.ranniservice.order.mapper.OrderMapper;
import com.ymchen.ranniservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public String createOrder(Long userId, String goodsNo) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        String orderNo = "NO" + snowFlake.nextId();
        Order order = new Order();
        order.setOrderUser(userId).setOrderGoods(goodsNo).setOrderNo(orderNo);
        int insert = baseMapper.insert(order);
        return orderNo;
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
