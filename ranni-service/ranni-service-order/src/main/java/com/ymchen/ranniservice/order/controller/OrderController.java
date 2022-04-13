package com.ymchen.ranniservice.order.controller;


import com.ymchen.ranniservice.order.entity.Order;
import com.ymchen.ranniservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("getById")
    public Order getById(@RequestParam("orderId") Long orderId) {
        return orderService.getById(orderId);
    }
}
