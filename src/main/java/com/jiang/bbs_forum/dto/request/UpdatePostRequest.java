package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
/** 修改帖子请求 */
public class UpdatePostRequest {
    /**
     * 标题
     */
    private String title;
    /**
     * 内容（富文本HTML）
     */
    private String content;
}
