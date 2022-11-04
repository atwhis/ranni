package com.ymchen.ranniservice.api.kafka.consumer;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer {


    @KafkaListener(topics = "orderStatus",groupId = "ranni-service-api")
    public void orderStatusChange(ConsumerRecord record) {
        log.info("consumer---> topic:{},value:{}", record.topic(), record.value());
    }
}
