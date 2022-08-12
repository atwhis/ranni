package com.ymchen.ranni.component.ossstarter.service.impl;

import com.ymchen.ranni.component.ossstarter.service.OssService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinioOssService implements OssService {

    @Override
    public String upload() {
        log.info("minio file upload");
        return null;
    }

    @Override
    public void download() {
        log.info("minio file download");
    }
}
