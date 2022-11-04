package com.ymchen.ranni.component.kafka.aspect;


import com.ymchen.ranni.component.kafka.annotation.KafkaProducer;
import com.ymchen.ranni.component.kafka.util.KafkaTopicUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@NoArgsConstructor
public class KafkaProducerAspect {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private KafkaTopicUtil kafkaTopicUtil;


    @Pointcut("@annotation(com.ymchen.ranni.component.kafka.annotation.KafkaProducer)")
    public void kafkaProducer() {
    }


    @Around(value = "kafkaProducer()")
    public void cacheResult(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method cacheMethod = methodSignature.getMethod();
        KafkaProducer kafkaProducer = cacheMethod.getAnnotation(KafkaProducer.class);
        Object[] args = pjp.getArgs();
        if (null != args && args.length == 1) {
            kafkaTopicUtil.createTopic(kafkaProducer.topic(), 1, (short) 1);
            kafkaTemplate.send(kafkaProducer.topic(), kafkaProducer, args[0]);
        }
    }

}
