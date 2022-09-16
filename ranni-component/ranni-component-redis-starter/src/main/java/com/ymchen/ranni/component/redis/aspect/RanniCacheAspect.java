package com.ymchen.ranni.component.redis.aspect;


import com.ymchen.ranni.component.redis.annotation.RanniCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Slf4j
public class RanniCacheAspect {

    @Pointcut("@annotation(com.ymchen.ranni.component.redis.annotation.RanniCache)")
    public void cache() {
    }

    @Around(value = "cache()")
    public Object cacheResult(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        Class<?> cacheClass = pjp.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method cacheMethod = cacheClass.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        RanniCache ranniCache = cacheMethod.getAnnotation(RanniCache.class);


        result=pjp.proceed();
        return result;
    }


    private String getKey(RanniCache ranniCache) {


        return null;
    }

}
