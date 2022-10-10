package com.ymchen.ranni.component.redis.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RanniCacheEvict {

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

}
