package com.ymchen.ranniservice.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.ymchen.ranniservice.crm.mapper")
public class RanniServiceCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(RanniServiceCrmApplication.class, args);
    }

}
