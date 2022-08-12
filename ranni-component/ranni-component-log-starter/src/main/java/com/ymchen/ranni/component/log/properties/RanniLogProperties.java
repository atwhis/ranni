package com.ymchen.ranni.component.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ranni.log")
public class RanniLogProperties {

    /**
     * 是否开启日志记录
     */
    private Boolean enableLogRecord;

    /**
     * 是否通过Logstash发送到ELK
     */
    private Boolean sendToELK;

    /**
     * logstash地址
     */
    private String logstashHost = "127.0.0.1:4560";
}
