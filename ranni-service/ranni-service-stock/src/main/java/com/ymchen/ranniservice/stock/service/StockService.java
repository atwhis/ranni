package com.ymchen.ranniservice.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymchen.rannibase.entity.stock.Stock;

public interface StockService extends IService<Stock> {

    Stock getByGoodsNo(String goodsNo);

    Stock getById(Long stockId);

    void deduct(String goodsNo);
}
