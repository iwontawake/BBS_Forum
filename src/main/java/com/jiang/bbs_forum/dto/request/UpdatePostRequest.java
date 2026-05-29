package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
}
