package com.ymchen.ranniservice.api.remote.fallback;

import com.ymchen.ranniservice.api.remote.StockRemoteService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockRemoteServiceFallback implements FallbackFactory<StockRemoteService> {
    @Override
    public StockRemoteService create(Throwable throwable) {
        throw new RuntimeException("扣减库存失败，回滚...");
        /*return goodsNo -> {
            log.error("调用扣减库存失败", throwable);
            throw new RuntimeException("调用扣减库存失败");
        };*/
    }
}
