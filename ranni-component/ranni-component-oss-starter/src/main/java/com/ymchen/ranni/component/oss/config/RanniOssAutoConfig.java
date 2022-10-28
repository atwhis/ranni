package com.ymchen.ranni.component.oss.config;


import com.ymchen.ranni.component.oss.properties.RanniOssProperties;
import com.ymchen.ranni.component.oss.service.OssService;
import com.ymchen.ranni.component.oss.service.impl.MinioOssService;
import com.ymchen.ranni.component.oss.service.impl.QiniuOssService;
import io.minio.MinioClient;
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
    public MinioOssService minioService(RanniOssProperties ranniOssProperties) {
        log.info("mino ranniOssProperties:{}",ranniOssProperties);
        return new MinioOssService();
    }

    @Bean
    @ConditionalOnExpression("'${ranni.oss.type}'.equals('minio')")
    public MinioClient minioClient(RanniOssProperties ranniOssProperties) {
        return MinioClient.builder()
                .endpoint(ranniOssProperties.getUrl())
                .credentials(ranniOssProperties.getAccessKey(), ranniOssProperties.getSecretKey())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(OssService.class)
    @ConditionalOnExpression("'${ranni.oss.type}'.equals('qiniu')")
    public QiniuOssService qiniu(RanniOssProperties ranniOssProperties) {
        log.info("qiniu ranniOssProperties:{}",ranniOssProperties);
        return new QiniuOssService();
    }
}
