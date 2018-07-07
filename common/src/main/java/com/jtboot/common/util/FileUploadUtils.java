package com.jtboot.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 1.分文件夹存储
 * 2.更改名称
 * 3.将文件写盘
 */
public class FileUploadUtils {

    private static final ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static String save(MultipartFile uploadFile, String dir) throws IOException {
        //以时间为文件夹名
        StringBuilder sb = makeDir(dir);

        //设置新文件名以UUID
        String newName = newFileName(uploadFile.getOriginalFilename());
        sb.append("/").append(newName);

        File saveFile = new File(sb.toString());

        uploadFile.transferTo(saveFile);

        //返回处理
        sb.delete(0, dir.length());
        if (sb.charAt(0) == '/') {
            sb.delete(0, 1);
        }
        return sb.toString();
    }

    private static StringBuilder makeDir(String dir) {
        StringBuilder sb = new StringBuilder(dir);

        if (dir.endsWith("/")) {
            sb.append(tl.get().format(new Date()));
        } else
            sb.append("/").append(tl.get().format(new Date()));

        File saveFile = new File(sb.toString());

        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }

        return sb;
    }

    private static String newFileName(String oldFileName) {
        return UUID.randomUUID().toString() + type(oldFileName);
    }

    private static String type(String str) {
        int point = str.lastIndexOf('.');
        return str.substring(point);
    }

}
