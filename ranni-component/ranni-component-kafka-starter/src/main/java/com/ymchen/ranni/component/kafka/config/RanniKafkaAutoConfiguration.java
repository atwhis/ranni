package com.ymchen.ranni.component.kafka.config;


import com.ymchen.ranni.component.kafka.aspect.KafkaProducerAspect;
import com.ymchen.ranni.component.kafka.util.KafkaTopicUtil;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(KafkaProperties.class)
public class RanniKafkaAutoConfiguration {

    @Bean
    public KafkaTopicUtil kafkaTopicUtil() {
        return new KafkaTopicUtil();
    }

    @Bean
    public KafkaProducerAspect kafkaProducerAspect() {
        return new KafkaProducerAspect();
    }
}
