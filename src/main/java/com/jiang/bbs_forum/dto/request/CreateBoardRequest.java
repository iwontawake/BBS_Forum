package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
/** 新增板块请求（管理员） */
public class CreateBoardRequest {
    /**
     * 板块名称
     */
    @NotBlank(message = "板块名称不能为空")
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
