package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCommentRequest {
    @NotBlank(message = "内容不能为空")
    private String content;
}
