package com.ymchen.ranniservice.stock.controller;


import com.ymchen.ranniservice.stock.entity.Stock;
import com.ymchen.ranniservice.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("getById")
    public Stock getById(@RequestParam("stockId") Long stockId) {
        return stockService.getById(stockId);
    }
}
