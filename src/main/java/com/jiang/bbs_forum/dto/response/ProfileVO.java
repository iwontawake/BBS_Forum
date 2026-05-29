package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/** 用户资料响应 */
public class ProfileVO {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像URL
     */
    private String avatar;
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
