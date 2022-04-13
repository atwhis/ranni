package com.ymchen.ranniservice.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.ymchen.ranniservice.stock.mapper")
public class RanniServiceStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RanniServiceStockApplication.class, args);
    }

}
