package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    ApiResponse<?> uploadAvatar(MultipartFile file);
    ApiResponse<?> uploadPostImage(MultipartFile file);
}
