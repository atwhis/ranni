package com.ymchen.ranni.component.ossstarter.service.impl;

import com.ymchen.ranni.component.ossstarter.properties.RanniOssProperties;
import com.ymchen.ranni.component.ossstarter.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class QiniuOssService implements OssService {

    @Autowired
    private RanniOssProperties ranniOssProperties;

    @Override
    public String upload() {
        log.info("qiniu file upload:{}",ranniOssProperties);
        return null;
    }

    @Override
    public void download() {
        log.info("qiniu file download");
    }
}
