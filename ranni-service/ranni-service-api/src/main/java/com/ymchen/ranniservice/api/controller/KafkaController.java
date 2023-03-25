package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.kafka.util.KafkaTopicUtil;
import com.ymchen.ranniservice.api.kafka.producer.OrderProducer;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("kafka")
@RequiredArgsConstructor
@Validated
public class KafkaController {

    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private KafkaTopicUtil kafkaTopicUtil;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @ApiOperation("获取所有topic")
    @PostMapping("getAllTopic")
    public Object getAllTopic() {
        return kafkaTopicUtil.getAllTopic();
    }


    @ApiOperation("订单状态变更")
    @GetMapping("orderStatusChange")
    public void OrderStatusChange(String msg) {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setOrderNo("test-123456");
//        orderDTO.setStatus(orderStatus);
        orderProducer.orderStatusChange(msg);
//        kafkaTemplate.send("orderStatus", msg);
    }


}
