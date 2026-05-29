package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;

public interface FavoriteService {
    ApiResponse<?> favorite(int userId, int postId);
    ApiResponse<?> unfavorite(int userId, int postId);
}
