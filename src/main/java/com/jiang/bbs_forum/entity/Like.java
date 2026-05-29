package com.jiang.bbs_forum.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("likes")
public class Like {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer targetType;
    private Integer targetId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
