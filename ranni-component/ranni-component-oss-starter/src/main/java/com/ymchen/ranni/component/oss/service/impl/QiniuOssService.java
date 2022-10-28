package com.ymchen.ranni.component.oss.service.impl;

import com.ymchen.ranni.component.oss.properties.RanniOssProperties;
import com.ymchen.ranni.component.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

@Slf4j
public class QiniuOssService implements OssService {

    @Autowired
    private RanniOssProperties ranniOssProperties;

    @Override
    public String upload(InputStream inputStream, String filename) {
        return null;
    }

    @Override
    public InputStream download(String filename) {
        return null;
    }
}
