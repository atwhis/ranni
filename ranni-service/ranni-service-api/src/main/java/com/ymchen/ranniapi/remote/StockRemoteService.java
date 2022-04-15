package com.ymchen.ranniapi.remote;


import com.ymchen.ranniapi.remote.fallback.StockRemoteServiceFallback;
import com.ymchen.rannibase.constant.RanniApplicationConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = RanniApplicationConstant.RANNI_SERVICE_STOCK, contextId = "stockRemoteService",fallbackFactory = StockRemoteServiceFallback.class)
public interface StockRemoteService {

    @GetMapping("/stock/deduct")
    public void deduct(@RequestParam("goodsNo") String goodsNo);
}
