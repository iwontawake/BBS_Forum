package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
/** 用户注册请求 */
public class RegisterRequest {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
