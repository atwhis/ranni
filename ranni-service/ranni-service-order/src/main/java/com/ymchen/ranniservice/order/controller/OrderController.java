package com.ymchen.ranniservice.order.controller;


import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.rannibase.entity.order.Order;
import com.ymchen.ranniservice.order.service.OrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@ApiOperation("订单controller")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("test")
    @ApiOperation("测试异常")
    public Object test() {
        System.out.printf("abc"+100/0);
        return "hello";
    }

    @ApiOperation("查询订单信息")
    @ApiImplicitParam(name="orderId",value = "订单Id")
    @GetMapping("getById")
    public Order getById(@RequestParam("orderId") Long orderId) {
        return orderService.getById(orderId);
    }

    @ApiOperation("查询用户所有订单")
    @ApiImplicitParam(name="userId",value = "用户Id",required = true,dataType = "Long")
    @GetMapping("getOrdersByUser")
    public List<Order> getOrdersByUser(@RequestParam("userId") Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("getOrderDetail")
    public OrderDTO getOrderDetail(@RequestParam("orderId") Long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @GetMapping("createOrder")
    public void createOrder(@RequestParam("userId") Long userId) {
        orderService.createOrder(userId);
    }

}
