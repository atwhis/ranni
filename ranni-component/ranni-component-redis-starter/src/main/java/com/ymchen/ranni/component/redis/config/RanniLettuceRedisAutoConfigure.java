package com.ymchen.ranni.component.redis.config;

import com.ymchen.ranni.component.redis.properties.RanniLettuceRedisProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(RanniLettuceRedisProperties.class)
@ConditionalOnProperty(value = "ranni.lettuce.redis.enable", havingValue = "true", matchIfMissing = true)
public class RanniLettuceRedisAutoConfigure {


}
