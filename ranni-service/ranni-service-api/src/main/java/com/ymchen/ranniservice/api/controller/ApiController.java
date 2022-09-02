package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.log.annotation.LogRecord;
import com.ymchen.ranni.component.ossstarter.service.OssService;
import com.ymchen.ranni.component.redis.util.RedisUtil;
import com.ymchen.rannibase.dto.api.UserOrderDTO;
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
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api")
@RefreshScope
@RequiredArgsConstructor
@Api("业务层controller")
public class ApiController {

    private final ApiService apiService;

    private final RedisUtil redisUtil;

    private final OssService ossService;

    @Value("${member.base.name}")
    private String memberName;

    @Value("${member.addr.city}")
    private String memberCity;

    @ApiOperation("测试从nacos获取配置")
    @GetMapping("getInfo")
    public Object getInfo() {
        log.error("memberName:{} , memberCity:{}", memberName, memberCity);
        return RanniResult.SUCCESS("memberName: " + memberName + " memberCity: " + memberCity);
    }

    @GetMapping("log")
    @ApiOperation("测试日志级别")
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

    //@GlobalTransactional
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

    @GetMapping("flow")
    @ApiOperation("测试流控")
    public Object testFlowControl() {
        return "flow control";
    }

    @GetMapping("degrade")
    @ApiOperation("测试降级")
    public Object testDegrade() {
        return "degrade";
    }

    @ApiOperation("skywalking链路测试")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long")
    @GetMapping("skywalkingTest")
    public Object skywalkingTest(@RequestParam("userId") Long userId) {
        redisUtil.set("hello", "world", 60L);
        final UserOrderDTO userOrders = apiService.getUserOrders(userId);
        redisUtil.get("hello");
        return userOrders;
    }

    @PostMapping("logTest")
    @ApiOperation("测试日志记录")
    @LogRecord(content = "日志和链路测试controller,用户id：{#userOrderDTO.userId},用户名：{#userOrderDTO.userName}")
    public Object testLogRecord(UserOrderDTO userOrderDTO) {
        apiService.logTest(userOrderDTO);
        return userOrderDTO;
    }

}
