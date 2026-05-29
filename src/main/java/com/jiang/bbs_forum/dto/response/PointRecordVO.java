package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointRecordVO {
    private Integer id;
    private Integer type;
    private String reason;
    private Integer points;
    private Integer balance;
    private String createTime;
}
