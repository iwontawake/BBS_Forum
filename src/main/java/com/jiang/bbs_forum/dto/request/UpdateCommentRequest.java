package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
/** 修改回复请求 */
public class UpdateCommentRequest {
    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;
}
