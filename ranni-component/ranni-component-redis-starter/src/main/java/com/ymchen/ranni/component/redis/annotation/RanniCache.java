package com.ymchen.ranni.component.redis.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RanniCache {

    /**
     * key的前缀
     *
     * @return
     */
    String keyPrefix();

    /**
     * key后缀
     *
     * @return
     */
    String keySuffix() default "";

    /**
     * 过期时间(s),默认10s
     *
     * @return
     */
    long seconds() default 10L;
}
