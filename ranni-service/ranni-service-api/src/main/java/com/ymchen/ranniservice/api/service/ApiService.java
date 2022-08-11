package com.ymchen.ranniservice.api.service;

import com.ymchen.rannibase.dto.api.UserOrderDTO;

public interface ApiService {
    UserOrderDTO getUserOrders(Long userId);

    String createOrderAndDeduct(Long userId,String goodsNo);

    void logTest(UserOrderDTO userOrderDTO);
}
