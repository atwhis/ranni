package com.ymchen.ranni.component.redis.config;


import com.ymchen.ranni.component.redis.util.RedisLockUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonAutoConfiguration {

    private static final Integer DEFAULT_TIME_OUT = 10000;
    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    private final RedisProperties redisProperties;

    public RedissonAutoConfiguration(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    @ConditionalOnBean(name = "redisTemplate")
    public RedissonClient redissonClient() throws IOException {
        int timeout = (null == redisProperties.getTimeout()) ? DEFAULT_TIME_OUT : (int) redisProperties.getTimeout().toMillis();
        String password = StringUtils.isEmpty(redisProperties.getPassword()) ? null : redisProperties.getPassword();
        Config config = new Config();
//        config.setLockWatchdogTimeout(9000);
        if (null != redisProperties.getCluster() && !CollectionUtils.isEmpty(redisProperties.getCluster().getNodes())) {
            config.useClusterServers()
                    .addNodeAddress(convert(redisProperties.getCluster().getNodes()))
                    .setConnectTimeout(timeout)
                    .setPassword(password);

        } else if (null != redisProperties.getSentinel() && !CollectionUtils.isEmpty(redisProperties.getSentinel().getNodes())) {
            config.useSentinelServers()
                    .setMasterName(redisProperties.getSentinel().getMaster())
                    .addSentinelAddress(convert(redisProperties.getSentinel().getNodes()))
                    .setDatabase(redisProperties.getDatabase())
                    .setConnectTimeout(timeout)
                    .setPassword(password);
        } else {
            config.useSingleServer()
                    .setAddress(REDIS_PROTOCOL_PREFIX + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setConnectTimeout(timeout)
                    .setDatabase(redisProperties.getDatabase())
                    .setPassword(password);
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnBean(name = "redissonClient")
    public RedisLockUtil redisLockUtil() {
        return new RedisLockUtil();
    }

    private String[] convert(List<String> nodesObject) {
        List<String> nodes = new ArrayList<String>(nodesObject.size());
        for (String node : nodesObject) {
            if (!node.startsWith(REDIS_PROTOCOL_PREFIX) && !node.startsWith(REDISS_PROTOCOL_PREFIX)) {
                nodes.add(REDIS_PROTOCOL_PREFIX + node);
            } else {
                nodes.add(node);
            }
        }
        return nodes.toArray(new String[nodes.size()]);
    }
}
