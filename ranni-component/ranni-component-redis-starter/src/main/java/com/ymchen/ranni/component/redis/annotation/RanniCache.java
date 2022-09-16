package com.ymchen.ranni.component.redis.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RanniCache {

    /**
     * redis命名空间
     *
     * @return
     */
    int namespace();

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
    String keySuffix();

    /**
     * 过期时间(s)
     *
     * @return
     */
    long seconds = 10L;
}
