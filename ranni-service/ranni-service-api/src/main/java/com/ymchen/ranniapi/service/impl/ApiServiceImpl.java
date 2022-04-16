package com.ymchen.ranniapi.service.impl;

import com.ymchen.ranniapi.remote.OrderRemoteService;
import com.ymchen.ranniapi.remote.StockRemoteService;
import com.ymchen.ranniapi.remote.UserRemoteService;
import com.ymchen.ranniapi.service.ApiService;
import com.ymchen.rannibase.dto.api.UserOrderDTO;
import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.rannibase.entity.crm.User;
import com.ymchen.rannibase.entity.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public void createOrderAndDeduct(Long userId, String goodsNo) {
        orderRemoteService.createOrder(userId);
        stockRemoteService.deduct(goodsNo);
    }

    @Override
    public UserOrderDTO getUserOrders(Long userId) {
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        User user = userRemoteService.getById(userId.intValue());
        userOrderDTO.setUserId(userId);
        userOrderDTO.setUserName(user.getUserName());


        List<OrderDTO> orderDTOs = new ArrayList<>();

        List<Order> orders = orderRemoteService.getOrdersByUser(userId);
        /*orders.forEach((order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            orderDTOs.add(orderDTO);
        }));*/

        orderDTOs = orders.stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            return orderDTO;
        }).collect(Collectors.toList());


        userOrderDTO.setOrders(orderDTOs);
        return userOrderDTO;
    }
}
