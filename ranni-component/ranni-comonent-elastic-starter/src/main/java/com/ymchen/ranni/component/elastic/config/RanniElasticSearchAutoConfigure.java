package com.ymchen.ranni.component.elastic.config;


import com.ymchen.ranni.component.elastic.util.ESUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RanniElasticSearchAutoConfigure {

    @Bean
    public ESUtil esUtil() {
        return new ESUtil();
    }
}
