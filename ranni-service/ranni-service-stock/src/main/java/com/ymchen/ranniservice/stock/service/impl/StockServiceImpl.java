package com.ymchen.ranniservice.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymchen.ranniservice.stock.entity.Stock;
import com.ymchen.ranniservice.stock.mapper.StockMapper;
import com.ymchen.ranniservice.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Override
    public Stock getById(Long stockId) {
        return baseMapper.selectById(stockId);
    }
}
