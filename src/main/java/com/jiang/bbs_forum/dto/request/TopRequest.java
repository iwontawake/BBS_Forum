package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
/** 置顶/取消置顶请求（管理员） */
public class TopRequest {
    /**
     * 是否置顶（1-置顶, 0-取消）
     */
    private Integer isTop;
}
