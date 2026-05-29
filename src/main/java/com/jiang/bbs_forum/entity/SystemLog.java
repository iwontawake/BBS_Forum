package com.jiang.bbs_forum.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("system_logs")
public class SystemLog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer adminId;
    private String operation;
    private String ip;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
