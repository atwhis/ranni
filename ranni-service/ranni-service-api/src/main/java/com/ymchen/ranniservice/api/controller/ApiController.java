package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranniservice.api.service.ApiService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api")
@RefreshScope
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @Value("${member.base.name}")
    private String memberName;

    @Value("${member.addr.city}")
    private String memberCity;

    @GetMapping("getInfo")
    public void getInfo() {
        log.error("memberName:{} , memberCity:{}", memberName, memberCity);
    }

    @GetMapping("log")
    public Object testLog() {
        log.error("test log......error");
        log.info("test log......info");
        log.warn("test log......warn");
        log.debug("test log......debug");
        return "hello world";
    }

    @GetMapping("getUserOrders")
    public Object getUserOrders(@RequestParam("userId") Long userId) {
        return apiService.getUserOrders(userId);
    }

    @GlobalTransactional
    @GetMapping("createOrder")
    public Object createOrderAndDeduct(@RequestParam("userId") Long userId, @RequestParam("goodsNo") String goodsNo) {
        apiService.createOrderAndDeduct(userId, goodsNo);
        return "hello1";
    }

}
