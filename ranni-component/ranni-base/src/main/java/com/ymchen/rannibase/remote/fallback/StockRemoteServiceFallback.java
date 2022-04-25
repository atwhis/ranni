package com.ymchen.rannibase.remote.fallback;

import com.ymchen.rannibase.entity.stock.Stock;
import com.ymchen.rannibase.remote.StockRemoteService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockRemoteServiceFallback implements FallbackFactory<StockRemoteService> {
    @Override
    public StockRemoteService create(Throwable throwable) {
        return new StockRemoteService() {
            @Override
            public void deduct(String goodsNo) {
                log.error("扣减库存失败。。。。goodsNo:{}",goodsNo);
            }

            @Override
            public Stock getByGoodsNo(String goodsNo) {
                log.error("查询库存信息失败。。。。goodsNo:{}",goodsNo);
                return null;
            }
        };
    }
}
