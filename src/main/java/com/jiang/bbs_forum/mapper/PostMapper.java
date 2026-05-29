package com.jiang.bbs_forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.bbs_forum.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
