package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
/** 修改密码请求 */
public class ChangePasswordRequest {
    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;
    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
