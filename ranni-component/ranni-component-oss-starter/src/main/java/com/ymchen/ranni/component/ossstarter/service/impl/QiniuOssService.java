package com.ymchen.ranni.component.ossstarter.service.impl;

import com.ymchen.ranni.component.ossstarter.properties.RanniOssProperties;
import com.ymchen.ranni.component.ossstarter.service.OssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class QiniuOssService implements OssService {

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
