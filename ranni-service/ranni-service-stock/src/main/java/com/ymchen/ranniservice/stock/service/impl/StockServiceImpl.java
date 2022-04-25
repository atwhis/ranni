package com.ymchen.ranniservice.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymchen.rannibase.entity.stock.Stock;
import com.ymchen.rannibase.exception.RanniBusinessException;
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
    public Stock getByGoodsNo(String goodsNo) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_no", goodsNo);
        List<Stock> stocks = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(stocks)) {
            return stocks.get(0);
        }
        return null;
    }

    @Override
    public Stock getById(Long stockId) {
        return baseMapper.selectById(stockId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deduct(String goodsNo) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_no", goodsNo);
        List<Stock> stocks = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(stocks)) {
            final Stock stock = stocks.get(0);
            Integer stockNum = stock.getStock();
            if (0 >= stockNum) {
                throw new RanniBusinessException(stock.getGoodsName() + " 库存不足");
            }
            UpdateWrapper<Stock> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("goods_no", goodsNo).set("stock", stockNum - 1);
            baseMapper.update(null, updateWrapper);
        } else {
            log.warn("产品编号: {}, 未查询到对应库存", goodsNo);
        }

    }
}
