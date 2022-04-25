package com.ymchen.rannibase.remote;


import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.entity.stock.Stock;
import com.ymchen.rannibase.remote.fallback.StockRemoteServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = RanniApplicationConstant.RANNI_SERVICE_STOCK, contextId = "stockRemoteService", fallbackFactory = StockRemoteServiceFallback.class)
public interface StockRemoteService {

    @GetMapping("/stock/deduct")
    void deduct(@RequestParam("goodsNo") String goodsNo);

    @GetMapping("/stock/getByGoodsNo")
    public Stock getByGoodsNo(@RequestParam("goodsNo") String goodsNo);


}
