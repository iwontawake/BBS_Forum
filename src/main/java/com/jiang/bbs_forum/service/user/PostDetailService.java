package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.PostDetailVO;

public interface PostDetailService {

    /**
     * 获取帖子详情
     */
    Response<PostDetailVO> getPostDetail(Integer userId, int postId, int page, int size);

}