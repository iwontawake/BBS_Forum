package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.UploadVO;
import com.jiang.bbs_forum.service.user.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 上传头像（限制 2MB，jpg/png/gif）
     */
    @PostMapping("/avatar")
    public Response<UploadVO> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return fileUploadService.uploadAvatar(file);
    }

    /**
     * 上传帖子图片（限制 5MB，jpg/png/gif）
     */
    @PostMapping("/post")
    public Response<UploadVO> uploadPostImage(@RequestParam("file") MultipartFile file) {
        return fileUploadService.uploadPostImage(file);
    }
}
