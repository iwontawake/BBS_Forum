package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
public class UpdateBoardRequest {
    private String name;
    private String description;
    private Integer sort;
}
