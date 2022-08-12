package com.ymchen.ranni.component.ossstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
