package com.ymchen.ranniservice.api.controller;

import com.ymchen.rannibase.entity.base.RanniResult;
import com.ymchen.rannibase.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("executor")
@Slf4j
public class ExecutorController {

    private final ThreadPoolTaskExecutor executor;

    private final ThreadPoolTaskScheduler scheduler;

    @ApiOperation("测试executor")
    @GetMapping("testExecutor")
    public Object testExecutor() {
        log.info("executor test");
        executor.execute(() -> {
            log.info("executor test execute");
        });
        return RanniResult.SUCCESS();
    }

    @ApiOperation("测试scheduler")
    @GetMapping("testScheduler")
    public Object testScheduler() {
        log.info("scheduler test");
        scheduler.schedule(() -> {
            log.info("scheduler test execute");
        }, DateUtil.toDate(LocalDateTime.now().plusSeconds(10)));
        return RanniResult.SUCCESS();
    }


}
