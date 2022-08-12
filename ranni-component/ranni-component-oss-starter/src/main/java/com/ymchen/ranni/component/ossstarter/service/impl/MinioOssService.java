package com.ymchen.ranni.component.ossstarter.service.impl;

import com.ymchen.ranni.component.ossstarter.properties.RanniOssProperties;
import com.ymchen.ranni.component.ossstarter.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MinioOssService implements OssService {

    @Autowired
    private RanniOssProperties ranniOssProperties;


    @Override
    public String upload() {
        log.info("minio file upload:{}",ranniOssProperties);
        return null;
    }

    @Override
    public void download() {
        log.info("minio file download");
    }
}
