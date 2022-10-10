package com.ymchen.ranni.component.redis.aspect;


import com.ymchen.ranni.component.redis.annotation.RanniCache;
import com.ymchen.ranni.component.redis.annotation.RanniCacheEvict;
import com.ymchen.ranni.component.redis.entity.NullCacheObject;
import com.ymchen.ranni.component.redis.properties.RanniLettuceRedisProperties;
import com.ymchen.ranni.component.redis.util.RedisLockUtil;
import com.ymchen.ranni.component.redis.util.RedisUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@NoArgsConstructor
public class RanniCacheAspect {

    @Autowired
    private RedisLockUtil redisLockUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RanniLettuceRedisProperties ranniLettuceRedisProperties;

    private static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("{", "}");
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private static final String KEY_SPLIT = "_";
    private static final String LOCK_KEY_SUFFIX = "_cache";

    @Pointcut("@annotation(com.ymchen.ranni.component.redis.annotation.RanniCache)")
    public void cacheAble() {
    }

    @Pointcut("@annotation(com.ymchen.ranni.component.redis.annotation.RanniCacheEvict)")
    public void cacheEvict() {
    }

    @Around(value = "cacheAble()")
    public Object cacheResult(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method cacheMethod = methodSignature.getMethod();
        RanniCache ranniCache = cacheMethod.getAnnotation(RanniCache.class);
        String cacheKey = ranniCache.keyPrefix() + getSuffixKey(ranniCache.keySuffix(), pjp);
        log.info("cache key:{}", cacheKey);

        Object cacheObject = getFromRedis(cacheKey);
        if (null != cacheObject) {
            if (cacheObject instanceof NullCacheObject) {
                return null;
            }
            return cacheObject;
        }
        return executeAndCache(pjp, cacheKey, ranniCache.seconds());
    }

    @Around(value = "cacheEvict()")
    public Object cacheEvict(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method cacheMethod = methodSignature.getMethod();
            RanniCacheEvict ranniCacheEvict = cacheMethod.getAnnotation(RanniCacheEvict.class);
            String cacheKey = ranniCacheEvict.keyPrefix() + getSuffixKey(ranniCacheEvict.keySuffix(), pjp);
            if (TransactionSynchronizationManager.isSynchronizationActive()) { // 当前线程事务未提交
                // 注册事务监听事件
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        log.info("等待事务-提交删除缓存 key:{} ,status:{}", cacheKey, status);
                        if (STATUS_COMMITTED == status) {
                            doubleDeleteFromRedis(cacheKey);
                        }
                    }
                });
            } else { // 事务已经提交直接刷新缓存
                log.info("事务已提交删除缓存 key:{}", cacheKey);
                doubleDeleteFromRedis(cacheKey);
            }
        } catch (Exception ex) {
            log.error("删除缓存异常", ex);
        }
        return result;
    }


    private void doubleDeleteFromRedis(String key) {
        redisUtil.del(key);
        log.info("首次删除缓存 key:{}", key);
        try {
            Thread.sleep(ranniLettuceRedisProperties.getMillSecondsToDelCache());
            redisUtil.del(key);
            log.info("延迟删除缓存 key:{}", key);
        } catch (Exception ex) {
            log.error("延迟删除缓存异常 key:{}", key, ex);
        }

    }


    private Object getFromRedis(String key) {
        return redisUtil.get(key);
    }


    /**
     * 防止缓存击穿(某一个key,雪崩是多个key同时失效)方法有:
     * 1.查询db缓存结果方法加锁(对并发性能有要求的,不建议使用)
     * 2.缓存数据长时间有效,过期时间当作数据本身的属性,逻辑过期时,异步刷新数据到缓存
     * ##缺点:数据长时间占用redis空间;首次访问没有数据时,还是有穿透的问题，需要数据主动预热或限流预热
     *
     * @param pjp
     * @param key
     * @param timeToLive
     * @return
     * @throws Throwable
     */
    private Object executeAndCache(ProceedingJoinPoint pjp, String key, Long timeToLive) throws Throwable {
        Object result = null;
        String lockKey = key + LOCK_KEY_SUFFIX;
        redisLockUtil.lockWithWatchdog(lockKey);
        try {
            result = pjp.proceed();
            writeToRedis(key, result, timeToLive);
        } finally {
            redisLockUtil.unlock(lockKey);
        }

        return result;
    }

    private void writeToRedis(String key, Object value, Long timeToLive) {
        try {
            if (null == value) {
                redisUtil.set(key, new NullCacheObject(), timeToLive);
            } else {
                redisUtil.set(key, value, timeToLive);
            }
        } catch (Exception ex) {
            log.error("缓存结果异常,key:{} , value:{}", key, value, ex);
        }
    }

    private String getSuffixKey(String keySuffix, ProceedingJoinPoint pjp) {
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Object[] args = pjp.getArgs();
            String[] parameterNames = signature.getParameterNames();
            StringBuilder key = new StringBuilder();
            if (args != null) {
                if (!"".equals(keySuffix.trim())) {
                    String[] expressions = keySuffix.split(KEY_SPLIT);
                    StandardEvaluationContext simpleContext = new StandardEvaluationContext();
                    for (int i = 0; i < args.length; i++) {
                        simpleContext.setVariable(parameterNames[i], args[i]);
                    }
                    if (expressions.length > 0) {
                        key.append(KEY_SPLIT);
                    }
                    Object value = EXPRESSION_PARSER.parseExpression(keySuffix, TEMPLATE_PARSER_CONTEXT).getValue(simpleContext);
                    key.append(value);
                }
            }
            return key.toString();
        } catch (Exception exception) {
            log.error("缓存key解析异常:", exception);
        }
        return "";
    }

}
