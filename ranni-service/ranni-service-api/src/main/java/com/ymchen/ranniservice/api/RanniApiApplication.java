package com.ymchen.ranniservice.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class RanniApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RanniApiApplication.class, args);
    }

}
