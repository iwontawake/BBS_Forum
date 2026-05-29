package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/** 登录/注册响应（token + 用户信息） */
public class LoginVO {
    /**
     * JWT令牌
     */
    private String token;
    /**
     * 过期时间
     */
    private String expireTime;
    /**
     * 用户信息
     */
    private UserVO user;
}
