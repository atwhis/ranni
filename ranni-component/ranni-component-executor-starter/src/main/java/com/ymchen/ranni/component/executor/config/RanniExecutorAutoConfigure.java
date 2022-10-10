package com.ymchen.ranni.component.executor.config;


import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@EnableConfigurationProperties(TaskExecutionProperties.class)
public class RanniExecutorAutoConfigure {

    @Bean
    @Primary
    public ThreadPoolTaskExecutor executor(TaskExecutionProperties executionProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executionProperties.getPool().getCoreSize());
        executor.setMaxPoolSize(executionProperties.getPool().getMaxSize());
        executor.setQueueCapacity(executionProperties.getPool().getQueueCapacity());
        executor.setKeepAliveSeconds((int) executionProperties.getPool().getKeepAlive().getSeconds());
        executor.setThreadNamePrefix(executionProperties.getThreadNamePrefix());
        executor.setWaitForTasksToCompleteOnShutdown(executionProperties.getShutdown().isAwaitTermination());
        if (null != executionProperties.getShutdown().getAwaitTerminationPeriod()) {
            executor.setAwaitTerminationSeconds((int) executionProperties.getShutdown().getAwaitTerminationPeriod().getSeconds());
        } else {
            executor.setAwaitTerminationSeconds(30);
        }
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    @Primary
    public ThreadPoolTaskScheduler taskScheduler(TaskExecutionProperties executionProperties) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(executionProperties.getPool().getCoreSize());
        taskScheduler.setThreadNamePrefix(executionProperties.getThreadNamePrefix());
        taskScheduler.setWaitForTasksToCompleteOnShutdown(executionProperties.getShutdown().isAwaitTermination());
        if (null != executionProperties.getShutdown().getAwaitTerminationPeriod()) {
            taskScheduler.setAwaitTerminationSeconds((int) executionProperties.getShutdown().getAwaitTerminationPeriod().getSeconds());
        }
        return taskScheduler;
    }
}
