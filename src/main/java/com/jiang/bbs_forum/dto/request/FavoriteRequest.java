package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequest {
    @NotNull(message = "帖子ID不能为空")
    private Integer postId;
}
