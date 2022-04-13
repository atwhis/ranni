package com.ymchen.ranniapi.controller;

import com.ymchen.ranniapi.service.TestService;
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
@RequestMapping("test")
@RefreshScope
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

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
        return testService.getUserOrders(userId);
    }


}
