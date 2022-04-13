package com.ymchen.ranniapi.remote;

import com.ymchen.ranniapi.remote.fallback.OrderRemoteServiceFallback;
import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.entity.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = RanniApplicationConstant.RANNI_SERVICE_ORDER, contextId = "orderRemoteService", fallbackFactory = OrderRemoteServiceFallback.class)
public interface OrderRemoteService {

    @GetMapping("/order/getOrdersByUser")
    public List<Order> getOrdersByUser(@RequestParam("userId") Long userId);

    @GetMapping("/order/createOrder")
    public void createOrder(@RequestParam("userId") Long userId);
}
