package com.ymchen.ranni.component.redis.aspect;


import com.ymchen.ranni.component.redis.annotation.RanniCache;
import com.ymchen.ranni.component.redis.entity.NullCacheObject;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@NoArgsConstructor
public class RanniCacheAspect {

    @Autowired
    private RedisLockUtil redisLockUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    public static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("{", "}");
    public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    @Pointcut("@annotation(com.ymchen.ranni.component.redis.annotation.RanniCache)")
    public void cache() {
    }

    @Around(value = "cache()")
    public Object cacheResult(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method cacheMethod = methodSignature.getMethod();
        RanniCache ranniCache = cacheMethod.getAnnotation(RanniCache.class);
        String cacheKey = ranniCache.keyPrefix() + getSuffixKey(ranniCache, pjp);
        log.error("cache key:{}", cacheKey);

        Object cacheObject = getFromRedis(cacheKey);
        if (null != cacheObject) {
            if (cacheObject instanceof NullCacheObject) {
                return null;
            }
            return cacheObject;
        } else {
            result = pjp.proceed();
            writeToRedis(cacheKey, result, ranniCache.seconds());
        }

        return result;
    }


    private Object getFromRedis(String key) {
        return redisUtil.get(key);
    }

    // redis 操作需要加锁???
    // 1.key中文?key编码?
    // 2.如何更新??主动删除缓存或更新?
    // 3.namespace ??
    private void writeToRedis(String key, Object value, Long timeToLive) {
        if (redisLockUtil.lockWithLeaseTime(key + "_cache", 10L, TimeUnit.SECONDS)) {
            if (timeToLive > 0) {
                executor.submit(() -> {
                    try {
                        if (null == value) {
                            redisUtil.set(key, new NullCacheObject(), timeToLive);
                        } else {
                            redisUtil.set(key, value, timeToLive);
                        }
                    } catch (Exception ex) {
                        log.error("缓存结果异常,key:{} , value:{}", key, value, ex);
                    }
                });
            }
        }
    }


    private String getSuffixKey(RanniCache ranniCache, ProceedingJoinPoint pjp) {
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Object[] args = pjp.getArgs();
            String[] parameterNames = signature.getParameterNames();
            StringBuilder key = new StringBuilder();
            if (args != null) {
                if (!"".equals(ranniCache.keySuffix().trim())) {
                    String[] expressions = ranniCache.keySuffix().split("_");
                    StandardEvaluationContext simpleContext = new StandardEvaluationContext();
                    for (int i = 0; i < args.length; i++) {
                        simpleContext.setVariable(parameterNames[i], args[i]);
                    }
                    if (expressions.length > 0) {
                        key.append("_");
                    }
                    Object value = EXPRESSION_PARSER.parseExpression(ranniCache.keySuffix(), TEMPLATE_PARSER_CONTEXT).getValue(simpleContext);
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
