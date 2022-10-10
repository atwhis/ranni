package com.ymchen.ranniservice.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.ymchen.ranniservice.order.mapper")
@EnableFeignClients(basePackages = "com.ymchen")
@EnableDiscoveryClient
public class RanniServiceOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RanniServiceOrderApplication.class, args);
    }

}
