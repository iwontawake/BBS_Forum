package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
/** 点赞请求（targetType: 1-帖子, 2-回复） */
public class LikeRequest {
    /**
     * 目标类型（1-帖子, 2-回复）
     */
    @NotNull(message = "目标类型不能为空")
    private Integer targetType;
    /**
     * 目标ID
     */
    @NotNull(message = "目标ID不能为空")
    private Integer targetId;
}
