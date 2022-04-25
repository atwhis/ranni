package com.ymchen.rannibase.remote.fallback;

import com.ymchen.rannibase.entity.order.Order;
import com.ymchen.rannibase.remote.OrderRemoteService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OrderRemoteServiceFallback implements FallbackFactory<OrderRemoteService> {
    @Override
    public OrderRemoteService create(Throwable throwable) {
        return new OrderRemoteService() {
            @Override
            public List<Order> getOrdersByUser(Long userId) {
                log.error("查询订单失败",throwable);
                return null;
            }

            @Override
            public String createOrder(Long userId) {
                log.error("创建订单失败",throwable);
                return null;
            }
        };
    }
}
