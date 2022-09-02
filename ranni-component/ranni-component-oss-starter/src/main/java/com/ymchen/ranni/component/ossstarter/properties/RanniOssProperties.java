package com.ymchen.ranni.component.ossstarter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ranni.oss")
public class RanniOssProperties {

    private String ossType;

    private String accessKey;

    private String secretKey;

    private String url;

    private String bucketName;

    private String region;

    private Boolean useHttps;
}
