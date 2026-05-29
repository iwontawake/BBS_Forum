package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserStatusRequest {
    @NotNull(message = "状态不能为空")
    private Integer status;  // 0-禁用, 1-正常
}
