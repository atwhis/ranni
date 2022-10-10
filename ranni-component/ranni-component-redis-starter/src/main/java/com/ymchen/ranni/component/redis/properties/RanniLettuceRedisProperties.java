package com.ymchen.ranni.component.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ranni.lettuce.redis")
public class RanniLettuceRedisProperties {

    /**
     * namespace 前2位产品线 后3位项目编码
     */
    private int namespace = 11000;

    /**
     * 是否开启Lettuce Redis
     */
    private Boolean enable = true;

    /**
     * 缓存延迟删除时间,单位毫秒
     */
    private Long millSecondsToDelCache = 700L;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


}
