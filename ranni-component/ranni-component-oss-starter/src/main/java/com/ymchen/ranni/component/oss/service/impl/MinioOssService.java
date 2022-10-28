package com.ymchen.ranni.component.oss.service.impl;

import com.ymchen.ranni.component.oss.properties.RanniOssProperties;
import com.ymchen.ranni.component.oss.service.OssService;
import com.ymchen.ranni.component.oss.util.OssFileUtil;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

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
