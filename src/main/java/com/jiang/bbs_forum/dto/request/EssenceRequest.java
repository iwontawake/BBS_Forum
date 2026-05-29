package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
/** 加精/取消加精请求（管理员） */
public class EssenceRequest {
    /**
     * 是否加精（1-加精, 0-取消）
     */
    private Integer isEssence;
}
