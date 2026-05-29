package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
/** 修改板块请求（管理员） */
public class UpdateBoardRequest {
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
}
