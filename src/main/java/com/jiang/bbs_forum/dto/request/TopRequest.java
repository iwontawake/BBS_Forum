package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
public class TopRequest {
    private Integer isTop;  // 1-置顶, 0-取消置顶
}
