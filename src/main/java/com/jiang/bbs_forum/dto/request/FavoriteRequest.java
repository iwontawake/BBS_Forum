package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
/** 收藏请求 */
public class FavoriteRequest {
    /**
     * 帖子ID
     */
    @NotNull(message = "帖子ID不能为空")
    private Integer postId;
}
