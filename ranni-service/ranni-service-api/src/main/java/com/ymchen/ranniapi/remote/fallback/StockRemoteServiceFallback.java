package com.ymchen.ranniapi.remote.fallback;

import com.ymchen.ranniapi.remote.StockRemoteService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockRemoteServiceFallback implements FallbackFactory<StockRemoteService> {
    @Override
    public StockRemoteService create(Throwable throwable) {
        return goodsNo -> {
            log.error("调用扣减库存失败", throwable);
        };
    }
}
