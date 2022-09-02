package com.ymchen.ranni.component.ossstarter.service;

import java.io.InputStream;

public interface OssService {


     String upload(InputStream inputStream,String filename);

     InputStream download(String filename);
}
