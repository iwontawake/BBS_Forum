package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/** 板块信息响应 */
public class BoardVO {
    /**
     * 板块ID
     */
    private Integer id;
    /**
     * 板块名称
     */
    private String name;
    /**
     * 板块描述
     */
    private String description;
    /**
     * 排序（数字越小越靠前）
     */
    private Integer sort;
    /**
     * 帖子数
     */
    private Integer postCount;
    /**
     * 创建时间
     */
    private String createTime;
}
