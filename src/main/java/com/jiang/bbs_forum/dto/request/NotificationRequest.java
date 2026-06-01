package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
public class NotificationRequest {

    private Integer fromUserId;   // 触发人
    private Integer toUserId;     // 接收人
    private Integer targetType;   // 1帖子 2评论
    private Integer targetId;     // 目标ID
    private String content;       // 可选内容
}