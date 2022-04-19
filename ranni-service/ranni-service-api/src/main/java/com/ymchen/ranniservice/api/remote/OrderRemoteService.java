package com.ymchen.ranniservice.api.remote;

import com.ymchen.ranniservice.api.remote.fallback.OrderRemoteServiceFallback;
import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.entity.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = RanniApplicationConstant.RANNI_SERVICE_ORDER, contextId = "orderRemoteService", fallbackFactory = OrderRemoteServiceFallback.class)
public interface OrderRemoteService {

    @GetMapping("/order/getOrdersByUser")
    List<Order> getOrdersByUser(@RequestParam("userId") Long userId);

    @GetMapping("/order/createOrder")
    void createOrder(@RequestParam("userId") Long userId);
}
