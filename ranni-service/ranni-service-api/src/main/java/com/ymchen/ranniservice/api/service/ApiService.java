package com.ymchen.ranniservice.api.service;

import com.ymchen.rannibase.dto.api.UserOrderDTO;

public interface ApiService {
    UserOrderDTO getUserOrders(Long userId);

    void createOrderAndDeduct(Long userId,String goodsNo);
}
