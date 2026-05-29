package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
/** 发布回复请求（一级回复/楼中楼） */
public class CreateCommentRequest {
    /**
     * 帖子ID
     */
    @NotNull(message = "帖子ID不能为空")
    private Integer postId;
    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 父回复ID（null为一级回复）
     */
    private Integer parentId;
}
