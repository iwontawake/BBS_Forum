package com.jiang.bbs_forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.bbs_forum.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
