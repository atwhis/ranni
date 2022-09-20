package com.ymchen.ranniservice.stock.controller;


import com.ymchen.ranni.component.redis.annotation.RanniCache;
import com.ymchen.ranni.component.redis.annotation.RanniCacheEvict;
import com.ymchen.rannibase.entity.stock.Stock;
import com.ymchen.ranniservice.stock.service.StockService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @ApiOperation("编号获取库存")
    @GetMapping("getByGoodsNo")
    @RanniCache(keyPrefix = "stock", keySuffix = "{#goodsNo}", seconds = 90)
    public Stock getByGoodsNo(@RequestParam("goodsNo") String goodsNo) {
        return stockService.getByGoodsNo(goodsNo);
    }

    @ApiOperation("id获取库存")
    @GetMapping("getById")
    public Stock getById(@RequestParam("stockId") Long stockId) {
        return stockService.getById(stockId);
    }

    @ApiOperation("扣减库存")
    @GetMapping("deduct")
    @RanniCacheEvict(keyPrefix = "stock", keySuffix = "{#goodsNo}")
    @Transactional
    public void deduct(@RequestParam("goodsNo") String goodsNo) {
        stockService.deduct(goodsNo);
        if (goodsNo.equals("00000")) {
            System.out.println(1 / 0);
        }
    }

    @ApiOperation("扣减库存--缓存测试")
    @GetMapping("deductForCacheTest")
    @Transactional
    @RanniCacheEvict(keyPrefix = "stock", keySuffix = "{#goodsNo}")
    public void deduct(@RequestParam("goodsNo1") String goodsNo1, @RequestParam("goodsNo2") String goodsNo2) {
        stockService.deduct(goodsNo1);
        stockService.deduct(goodsNo2);
        if (goodsNo1.equals("00000") || goodsNo2.equals("0000")) {
            System.out.println(1 / 0);
        }
    }
}
