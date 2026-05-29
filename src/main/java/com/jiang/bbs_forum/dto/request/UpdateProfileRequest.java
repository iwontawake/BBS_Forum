package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
/** 修改个人资料请求 */
public class UpdateProfileRequest {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 工作性质
     */
    private String workNature;
    /**
     * 工作地点
     */
    private String workLocation;
    /**
     * 个人签名
     */
    private String signature;
}
