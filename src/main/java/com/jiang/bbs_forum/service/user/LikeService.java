package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;

public interface LikeService {
    ApiResponse<?> like(int userId, int targetType, int targetId);
    ApiResponse<?> unlike(int userId, int targetType, int targetId);
}
