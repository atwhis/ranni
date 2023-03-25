package com.ymchen.ranni.component.kafka.aspect;


import com.ymchen.ranni.component.kafka.annotation.KafkaProducer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@NoArgsConstructor
public class KafkaProducerAspect {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Pointcut("@annotation(com.ymchen.ranni.component.kafka.annotation.KafkaProducer)")
    public void kafkaProducer() {
    }


    //TODO 是否有比接口定义再注入更好的方式?
    @Around(value = "kafkaProducer()")
    public void msgSend(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method msgSendMethod = methodSignature.getMethod();
        KafkaProducer kafkaProducer = msgSendMethod.getAnnotation(KafkaProducer.class);
        Object[] args = pjp.getArgs();
        if (null != args && args.length == 1) {
            //topic使用前先申请，根据实际broker和消费者个数设置对应分区和副本数，消费者数多于分区数时会造成消费者空闲浪费
            //kafkaTopicUtil.createTopic(kafkaProducer.topic(), 1, (short) 1);
            String key = StringUtils.isEmpty(kafkaProducer.key()) ? null : kafkaProducer.key();
            kafkaTemplate.send(kafkaProducer.topic(), key, args[0]).addCallback(new SuccessCallback() {
                @Override
                public void onSuccess(Object result) {
                    log.error("消息发送成功 topic:{}, key:{}, 消息详情:{}", kafkaProducer.topic(), kafkaProducer.key(), result);
                }
            }, new FailureCallback() {
                @Override
                public void onFailure(Throwable ex) {
                    log.error("消息发送异常 topic:{}, key:{}, 异常信息:{}", kafkaProducer.topic(), kafkaProducer.key(), ex);
                }
            });
        }
    }

}
