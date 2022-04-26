package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.redis.util.RedisUtil;
import com.ymchen.rannibase.entity.base.RanniResult;
import com.ymchen.rannibase.entity.order.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@ApiOperation("redis测试")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("redis")
public class RedisController {

    private final RedisUtil redisUtil;


    @ApiOperation("写数据到redis-set")
    @PostMapping("writeToRedis")
    public Object writeToRedis(@RequestParam("key") String key, @RequestParam("time") Long time, @RequestBody Order order) {
        redisUtil.set(key, order, time);
        return RanniResult.SUCCESS();
    }

    @ApiOperation("从redis获取数据-get")
    @GetMapping("getFromRedis")
    public Object getFromRedis(@RequestParam("key") String key) {
        final Object o = redisUtil.get(key);
        return RanniResult.SUCCESS(o);
    }

}
