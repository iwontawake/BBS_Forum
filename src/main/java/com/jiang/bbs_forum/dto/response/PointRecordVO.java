package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/** 积分记录响应 */
public class PointRecordVO {
    /**
     * 记录ID
     */
    private Integer id;
    /**
     * 类型（1-获得, 2-消耗）
     */
    private Integer type;
    /**
     * 变动原因
     */
    private String reason;
    /**
     * 变动积分
     */
    private Integer points;
    /**
     * 变动后余额
     */
    private Integer balance;
    /**
     * 创建时间
     */
    private String createTime;
}
