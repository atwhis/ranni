package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.ossstarter.service.OssService;
import com.ymchen.ranni.component.ossstarter.util.OssFileUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("oss")
public class OSSController {

    private final OssService ossService;


    @ApiOperation("hello")
    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation("测试文件上传")
    @PostMapping("upload")
    public Object upload(MultipartFile file) {
        String fileUrl = "";
        try {
            fileUrl = ossService.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (Exception ex) {
            log.error("upload error", ex);
        }
        return fileUrl;
    }

    @ApiOperation("测试文件下载")
    @RequestMapping("download")
    public Object download(@RequestParam("fileName") String fileUrl, HttpServletResponse response) throws Exception {
        String filename = OssFileUtil.getFilenameByFileUrl(fileUrl, "/");
        InputStream inputStream = ossService.download(filename);
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        OutputStream outStr = null;
        outStr = response.getOutputStream();
        byte[] buf = new byte[1024];
        int index = 0;
        while ((index = inputStream.read(buf)) != -1) {
            outStr.write(buf, 0, index);
            outStr.flush();
        }
        outStr.close();
        inputStream.close();
        return "success";
    }
}
