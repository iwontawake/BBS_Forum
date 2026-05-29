package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 禁用/启用用户、置顶/取消置顶、加精/取消加精 等状态变更的返回
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusVO {
    private Integer id;
    private Integer status;
    private Integer isTop;
    private Integer isEssence;
    private String updateTime;
}
