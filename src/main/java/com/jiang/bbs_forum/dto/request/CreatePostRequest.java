package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
/** 发布帖子请求（普通帖/需求帖） */
public class CreatePostRequest {
    /**
     * 板块ID
     */
    @NotNull(message = "板块ID不能为空")
    private Integer boardId;
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     * 内容（富文本HTML）
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 是否需求帖（0-普通帖, 1-需求帖）
     */
    private Integer isDemand;
    /**
     * 需求帖奖励积分
     */
    private Integer rewardPoints;
}
