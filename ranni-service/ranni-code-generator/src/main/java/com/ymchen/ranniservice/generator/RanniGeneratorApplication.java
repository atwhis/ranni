package com.ymchen.ranniservice.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ymchen.ranniservice.generator.mapper")
public class RanniGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RanniGeneratorApplication.class, args);
    }

}
