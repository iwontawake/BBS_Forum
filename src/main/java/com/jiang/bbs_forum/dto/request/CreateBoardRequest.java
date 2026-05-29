package com.jiang.bbs_forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBoardRequest {
    @NotBlank(message = "板块名称不能为空")
    private String name;
    private String description;
    private Integer sort;
}
