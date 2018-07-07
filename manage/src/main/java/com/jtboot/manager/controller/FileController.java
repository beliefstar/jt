package com.jtboot.manager.controller;

import com.jtboot.common.vo.PicUploadResult;
import com.jtboot.manager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public PicUploadResult imgageUp(MultipartFile uploadFile) {
        return fileService.pictureUp(uploadFile);
    }
}
