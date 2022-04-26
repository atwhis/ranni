package com.ymchen.ranniservice.api.controller;

import com.ymchen.rannibase.entity.base.RanniResult;
import com.ymchen.ranniservice.api.service.ApiService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api("业务层controller")
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

    @ApiOperation("查询用户订单")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long")
    @GetMapping("getUserOrders")
    public Object getUserOrders(@RequestParam("userId") Long userId) {
        return apiService.getUserOrders(userId);
    }

    @GlobalTransactional
    @GetMapping("createOrder")
    @ApiOperation("创建订单-正常事务")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "goodsNo", value = "商品编号", required = true, dataType = "String")})
    public Object createOrderAndDeduct(@RequestParam("userId") Long userId, @RequestParam("goodsNo") String goodsNo) {
        final String orderNo = apiService.createOrderAndDeduct(userId, goodsNo);
        return RanniResult.SUCCESS(orderNo);
    }

    @GlobalTransactional
    @GetMapping("createOrderRollback")
    @ApiOperation("创建订单-事务回滚(商品编号-N789)")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "goodsNo", value = "商品编号", required = false, dataType = "String", defaultValue = "N789")})
    public Object createOrderAndDeductRollback(@RequestParam("userId") Long userId, @RequestParam("goodsNo") String goodsNo) {
        final String orderNo = apiService.createOrderAndDeduct(userId, goodsNo);
        return RanniResult.SUCCESS(orderNo);
    }

}
