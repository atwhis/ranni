package com.ymchen.ranniservice.api.kafka.producer;

import com.ymchen.ranni.component.kafka.annotation.KafkaProducer;
import com.ymchen.rannibase.dto.order.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProducer {

    @KafkaProducer(topic = "orderStatus")
    public void orderStatusChange(OrderDTO orderDTO) {
        log.info("order status change...");
    }


}
