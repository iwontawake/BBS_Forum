package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.UploadVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    Response<UploadVO> uploadAvatar(MultipartFile file);
    Response<UploadVO> uploadPostImage(MultipartFile file);
}
