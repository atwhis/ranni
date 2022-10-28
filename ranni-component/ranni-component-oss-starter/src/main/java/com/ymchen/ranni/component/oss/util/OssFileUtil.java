package com.ymchen.ranni.component.oss.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class OssFileUtil {

    private OssFileUtil() {

    }

    public static String getFilenameByFileUrl(String fileUrl, String separateStr) {
        if (StringUtils.isNotBlank(separateStr)) {
            return fileUrl.substring(fileUrl.lastIndexOf(separateStr) + 1);
        }
        return fileUrl;
    }

    public static String generateNewFilename(String filename) {
        return UUID.randomUUID().toString().replaceAll("-", "")
                + filename.substring(filename.lastIndexOf("."));
    }
}
