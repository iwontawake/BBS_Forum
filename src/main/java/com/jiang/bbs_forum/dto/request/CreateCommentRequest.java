package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @NotNull(message = "帖子ID不能为空")
    private Integer postId;
    @NotBlank(message = "内容不能为空")
    private String content;
    private Integer parentId;
}
