package com.jtboot.manager.service;

import com.jtboot.common.vo.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    PicUploadResult pictureUp(MultipartFile img);
}
