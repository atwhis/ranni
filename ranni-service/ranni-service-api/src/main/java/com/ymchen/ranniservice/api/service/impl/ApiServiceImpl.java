package com.ymchen.ranniservice.api.service.impl;


import com.ymchen.rannibase.dto.api.UserOrderDTO;
import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.rannibase.entity.order.Order;
import com.ymchen.rannibase.entity.stock.Stock;
import com.ymchen.rannibase.remote.OrderRemoteService;
import com.ymchen.rannibase.remote.StockRemoteService;
import com.ymchen.rannibase.remote.UserRemoteService;
import com.ymchen.ranniservice.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ApiServiceImpl implements ApiService {

    private final UserRemoteService userRemoteService;

    private final OrderRemoteService orderRemoteService;

    private final StockRemoteService stockRemoteService;

    @Transactional
    @Override
    public String createOrderAndDeduct(Long userId, String goodsNo) {
        final String orderNo = orderRemoteService.createOrder(userId,goodsNo);
        stockRemoteService.deduct(goodsNo);
        return orderNo;
    }

    @Override
    public UserOrderDTO getUserOrders(Long userId) {
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        User user = userRemoteService.getById(userId.intValue());
        userOrderDTO.setUserId(userId);
        userOrderDTO.setUserName(user.getUserName());


        List<OrderDTO> orderDTOs;

        List<Order> orders = orderRemoteService.getOrdersByUser(userId);
        /*orders.forEach((order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            orderDTOs.add(orderDTO);
        }));*/

        orderDTOs = orders.stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);

            final Stock stock = stockRemoteService.getByGoodsNo(order.getOrderGoods());
            orderDTO.setUserName(user.getUserName());
            orderDTO.setGoodsName(stock.getGoodsName());

            return orderDTO;
        }).collect(Collectors.toList());


        userOrderDTO.setOrders(orderDTOs);
        return userOrderDTO;
    }
}
