package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.redis.util.RedisLockUtil;
import com.ymchen.ranni.component.redis.util.RedisUtil;
import com.ymchen.rannibase.entity.base.RanniResult;
import com.ymchen.rannibase.entity.order.Order;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@ApiOperation("redis测试")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("redis")
public class RedisController {

    private final RedisUtil redisUtil;

    private final RedisLockUtil redisLockUtil;

    @ApiOperation("测试自动过期锁")
    @PostMapping("testLeaseLock")
    public Object testLeaseLock(@RequestParam("lockName") String lockName) {
        try {
            redisLockUtil.lockWithLeaseTime(lockName, 3, TimeUnit.SECONDS);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
           redisLockUtil.unlock(lockName);
        }

        return Boolean.TRUE;
    }

    @ApiOperation("测试自动续期锁-业务耗时35s")
    @PostMapping("testWatchDogLock")
    public Object testWatchDogLock(@RequestParam("lockName") String lockName) {
        try {
            redisLockUtil.lockWithWatchdog(lockName);
            Thread.sleep(35000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            redisLockUtil.unlock(lockName);
        }

        return Boolean.TRUE;
    }


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
