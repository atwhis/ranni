package com.ymchen.ranniapi.remote.fallback;

import com.ymchen.ranniapi.remote.OrderRemoteService;
import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.entity.order.Order;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
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
            public void createOrder(Long userId) {
                log.error("创建订单失败",throwable);
            }
        };
    }
}
