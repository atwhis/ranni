package com.ymchen.ranni.component.redis.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class RedisLockUtil {


    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 锁无过期时间,根据lockWatchdogTimeOut(默认30000ms)时间,若客户端
     * 未主动释放锁(还有心跳),则到lockWatchdogTimeOut/3时会自动续期(刷新锁的过期时间)
     *
     * @param lockName lockName
     * @return
     */
    public void lockWithWatchdog(String lockName) {
        final RLock lock = redissonClient.getLock(lockName);
        lock.lock();
        LOGGER.info("acquire distributor lock success,lockName={}", lockName);
    }

    /**
     * 有过期时间的锁,可以不主动释放锁,等待过期;主动释放锁时如果过期了,会抛锁不存在异常
     *
     * @param lockName  lockName
     * @param leaseTime expired time
     * @param unit      expired time unit
     * @return
     */
    public Boolean lockWithLeaseTime(String lockName, long leaseTime, TimeUnit unit) {
        final RLock lock = redissonClient.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = lock.tryLock(0, leaseTime, unit);
            if (getLock) {
                LOGGER.info("acquire distributor lock success,lockName={}", lockName);
            } else {
                LOGGER.info("acquire distributor lock fail,lockName={}", lockName);
            }
        } catch (InterruptedException e) {
            LOGGER.error("acquire distributor lock exception，lockName=" + lockName, e);
            return false;
        }
        return getLock;
    }


    /**
     * 释放锁,捕获所有异常,不对外抛出
     * 目前想到的异常，调用方亦处理不了
     * 1、锁到期，释放时不存在
     * 2、连不上redis
     * 3、key存在，释放时异常
     *
     * @param lockName
     */
    public void unlock(String lockName) {
        try {
            RLock lock = redissonClient.getLock(lockName);
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
            LOGGER.info("unlock distributor lock success,lockName={}", lockName);
        } catch (Exception ex) {
            LOGGER.error("unlock distributor lock exception,lockName=" + lockName, ex);
        }

    }
}
