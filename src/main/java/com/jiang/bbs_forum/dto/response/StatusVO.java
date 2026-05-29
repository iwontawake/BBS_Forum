package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 状态变更响应（禁用/启用用户、置顶/取消置顶、加精/取消加精）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusVO {
    /**
     * 目标ID
     */
    private Integer id;
    /**
     * 状态（0-禁用, 1-正常）
     */
    private Integer status;
    /**
     * 是否置顶（0-否, 1-是）
     */
    private Integer isTop;
    /**
     * 是否加精（0-否, 1-是）
     */
    private Integer isEssence;
    /**
     * 更新时间
     */
    private String updateTime;
}
