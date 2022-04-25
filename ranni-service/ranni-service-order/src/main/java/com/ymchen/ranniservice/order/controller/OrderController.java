package com.ymchen.ranniservice.order.controller;


import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.rannibase.entity.order.Order;
import com.ymchen.ranniservice.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api("订单controller")
public class OrderController {

    private final OrderService orderService;

    @ApiOperation("主键查询订单信息")
    @ApiImplicitParam(name = "orderId", value = "订单Id")
    @GetMapping("getById")
    public Order getById(@RequestParam("orderId") Long orderId) {
        return orderService.getById(orderId);
    }

    @ApiOperation("查询用户所有订单")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long")
    @GetMapping("getOrdersByUser")
    public List<Order> getOrdersByUser(@RequestParam("userId") Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @ApiOperation("查询订单详情")
    @ApiImplicitParam(name = "orderId", value = "订单Id", required = true, dataType = "Long")
    @GetMapping("getOrderDetail")
    public OrderDTO getOrderDetail(@RequestParam("orderId") Long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @GetMapping("createOrder")
    @ApiOperation("创建订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "goodsNo", value = "商品编号", required = true, dataType = "String")})
    public String createOrder(@RequestParam("userId") Long userId, @RequestParam("goodsNo") String goodsNo) {
        return orderService.createOrder(userId, goodsNo);
    }

}
