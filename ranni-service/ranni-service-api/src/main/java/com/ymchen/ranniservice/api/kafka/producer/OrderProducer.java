package com.ymchen.ranniservice.api.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

//    @KafkaProducer(topic = "orderStatus",key = "测试123")
    public void orderStatusChange(String msg) {

        kafkaTemplate.send("orderStatus", "", msg);
        log.info("order status change...");
    }


}
