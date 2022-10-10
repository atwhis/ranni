package com.ymchen.ranni.component.ossstarter.service.impl;

import com.ymchen.ranni.component.ossstarter.properties.RanniOssProperties;
import com.ymchen.ranni.component.ossstarter.service.OssService;
import com.ymchen.ranni.component.ossstarter.util.OssFileUtil;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.Objects;

@Slf4j
public class MinioOssService implements OssService {

    @Autowired
    private RanniOssProperties ranniOssProperties;
    @Autowired
    private MinioClient minioClient;

    private void createBucketIfNoExist(MinioClient minioClient) {
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(ranniOssProperties.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(ranniOssProperties.getBucketName()).build());
            }
        } catch (Exception ex) {
            log.error("create bucket error", ex);
        }

    }

    @Override
    public String upload(InputStream inputStream, String filename) {
        try {
            createBucketIfNoExist(minioClient);
            String newFilename = OssFileUtil.generateNewFilename(filename);
            minioClient.putObject(PutObjectArgs.builder().bucket(ranniOssProperties.getBucketName())
                    .object(newFilename).stream(inputStream, inputStream.available(), -1).build());
            return ranniOssProperties.getUrl() + "/" + ranniOssProperties.getBucketName() + "/" + newFilename;
        } catch (Exception ex) {
            log.error("file upload error", ex);
        }
        return null;
    }

    @Override
    public InputStream download(String filename) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder().bucket(ranniOssProperties.getBucketName()).object(filename).build());
        } catch (Exception exception) {
            log.error("download file error", exception);
        }
        return null;
    }
}
