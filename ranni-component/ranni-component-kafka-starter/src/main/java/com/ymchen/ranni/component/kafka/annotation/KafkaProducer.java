package com.ymchen.ranni.component.kafka.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface KafkaProducer {
    String topic();

    String key() default "";
}
