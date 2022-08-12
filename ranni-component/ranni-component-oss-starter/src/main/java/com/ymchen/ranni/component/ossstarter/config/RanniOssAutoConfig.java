package com.ymchen.ranni.component.ossstarter.config;


import com.ymchen.ranni.component.ossstarter.properties.RanniOssProperties;
import com.ymchen.ranni.component.ossstarter.service.OssService;
import com.ymchen.ranni.component.ossstarter.service.impl.MinioOssService;
import com.ymchen.ranni.component.ossstarter.service.impl.QiniuOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableConfigurationProperties(RanniOssProperties.class)
public class RanniOssAutoConfig {


    @Bean
    @ConditionalOnMissingBean(OssService.class)
    @ConditionalOnExpression("'${ranni.oss.type}'.equals('minio')")
    public MinioOssService minio(RanniOssProperties ranniOssProperties) {
        log.info("mino ranniOssProperties:{}",ranniOssProperties);
        return new MinioOssService();
    }

    @Bean
    @ConditionalOnMissingBean(OssService.class)
    @ConditionalOnExpression("'${ranni.oss.type}'.equals('qiniu')")
    public QiniuOssService qiniu(RanniOssProperties ranniOssProperties) {
        log.info("qiniu ranniOssProperties:{}",ranniOssProperties);
        return new QiniuOssService();
    }
}
