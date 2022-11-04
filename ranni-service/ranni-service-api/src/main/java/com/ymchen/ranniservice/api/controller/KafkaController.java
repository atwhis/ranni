package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.kafka.util.KafkaTopicUtil;
import com.ymchen.rannibase.dto.order.OrderDTO;
import com.ymchen.ranniservice.api.kafka.producer.OrderProducer;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("获取所有topic")
    @PostMapping("getAllTopic")
    public Object getAllTopic() {
        return kafkaTopicUtil.getAllTopic();
    }


    @ApiOperation("订单状态变更")
    @GetMapping("orderStatusChange")
    public void OrderStatusChange(@RequestParam("orderStatus") Integer orderStatus) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNo("test-123456");
        orderDTO.setStatus(orderStatus);
        orderProducer.orderStatusChange(orderDTO);
    }


}
