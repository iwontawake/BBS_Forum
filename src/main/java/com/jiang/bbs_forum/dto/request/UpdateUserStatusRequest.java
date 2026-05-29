package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
/** 禁用/启用用户请求（管理员，status: 0-禁用, 1-正常） */
public class UpdateUserStatusRequest {
    /**
     * 状态（0-禁用, 1-正常）
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
