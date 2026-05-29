package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/** 管理员操作日志响应 */
public class LogVO {
    /**
     * 日志ID
     */
    private Integer id;
    /**
     * 管理员ID
     */
    private Integer adminId;
    /**
     * 管理员名称
     */
    private String adminName;
    /**
     * 操作内容
     */
    private String operation;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 创建时间
     */
    private String createTime;
}
