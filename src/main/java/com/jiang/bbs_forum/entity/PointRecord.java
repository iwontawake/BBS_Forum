package com.jiang.bbs_forum.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("point_records")
public class PointRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer type;
    private String reason;
    private Integer points;
    private Integer balance;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
