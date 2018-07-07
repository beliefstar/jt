package com.jtboot.manager.service.impl;

import com.jtboot.common.util.FileUploadUtils;
import com.jtboot.common.vo.PicUploadResult;
import com.jtboot.manager.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 文件上传
 *
 * 1.校验(病毒)
 * 2.判断是否非法程序
 * 3.分文件夹存储
 * 4.更改名称
 * 5.将文件写盘
 */
@Component
public class FileServiceImpl implements FileService {

    @Value("${urlPath}")
    private String urlPath;

    @Value("${localPath}")
    private String ioPath;

    @Override
    public PicUploadResult pictureUp(MultipartFile img) {
        PicUploadResult result = new PicUploadResult();
        //校验！！！
        try {
            /*
             * 判断是否为恶意程序
             */
            BufferedImage bufferImage = ImageIO.read(img.getInputStream());
            //获取高度
            int height = bufferImage.getHeight();
            //获取宽度
            int width = bufferImage.getWidth();

            if (height == 0 || width == 0) {
                //不是图片
                result.setError(1);
                return result;
            }
            result.setHeight(String.valueOf(height));
            result.setWidth(String.valueOf(width));
        } catch (Exception e) {
            result.setError(1);
            return result;
        }
        //校验--end

        String dir = ioPath + "img";

        try {
            String savePath = FileUploadUtils.save(img, dir);

            result.setError(0);
            result.setUrl(urlPath + savePath);
        } catch (IOException e) {
            e.printStackTrace();
            result.setError(1);
        }
        return result;
    }
}
