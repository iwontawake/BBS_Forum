package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogVO {
    private Integer id;
    private Integer adminId;
    private String adminName;
    private String operation;
    private String ip;
    private String createTime;
}
