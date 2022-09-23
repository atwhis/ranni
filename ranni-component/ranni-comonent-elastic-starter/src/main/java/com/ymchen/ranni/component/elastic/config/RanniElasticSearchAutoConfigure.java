package com.ymchen.ranni.component.elastic.config;


import com.ymchen.ranni.component.elastic.util.ESUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RanniElasticSearchAutoConfigure {

    @ConditionalOnBean(RestHighLevelClient.class)
    @Bean
    public ESUtil esUtil() {
        return new ESUtil();
    }
}
