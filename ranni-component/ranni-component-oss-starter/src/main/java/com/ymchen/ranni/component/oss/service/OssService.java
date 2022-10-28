package com.ymchen.ranni.component.oss.service;

import java.io.InputStream;

public interface OssService {


     String upload(InputStream inputStream,String filename);

     InputStream download(String filename);
}
