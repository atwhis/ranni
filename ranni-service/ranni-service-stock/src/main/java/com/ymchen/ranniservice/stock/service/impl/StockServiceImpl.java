package com.ymchen.ranniservice.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymchen.rannibase.entity.stock.Stock;
import com.ymchen.ranniservice.stock.mapper.StockMapper;
import com.ymchen.ranniservice.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Override
    public Stock getById(Long stockId) {
        return baseMapper.selectById(stockId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deduct(String goodsNo) {

        if (goodsNo.endsWith("abc")) {
            throw new RuntimeException("distributor transaction fail test");
        }

        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_no", goodsNo);
        List<Stock> stocks = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(stocks)) {
            Integer stock = stocks.get(0).getStock();
            UpdateWrapper<Stock> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("goods_no", goodsNo).set("stock", stock - 1);
            baseMapper.update(null, updateWrapper);
        } else {
            log.warn("产品编号: {}, 未查询到对应库存", goodsNo);
        }

    }
}
