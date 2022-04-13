package com.ymchen.ranniservice.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ymchen.rannibase.entity.stock.Stock;

public interface StockService extends IService<Stock> {

    Stock getById(Long stockId);
}
