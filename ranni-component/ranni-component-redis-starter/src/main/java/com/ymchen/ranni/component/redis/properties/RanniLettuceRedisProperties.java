package com.ymchen.ranni.component.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ranni.lettuce.redis")
public class RanniLettuceRedisProperties {

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
