package com.ymchen.ranniapi.service;

import com.ymchen.rannibase.dto.api.UserOrderDTO;

public interface ApiService {
    UserOrderDTO getUserOrders(Long userId);

    void createOrderAndDeduct(Long userId,String goodsNo);
}