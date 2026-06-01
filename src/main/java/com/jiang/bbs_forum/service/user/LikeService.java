package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.LikeBatchRequest;
import com.jiang.bbs_forum.dto.response.LikeBatchVO;

public interface LikeService {

    Response<Void> like(int userId, int targetType, int targetId);

    Response<Void> unlike(int userId, int targetType, int targetId);

    Response<Boolean> isLiked(int userId, int targetType, int targetId);

    Response<LikeBatchVO> batchStatus(int userId, LikeBatchRequest request);
}