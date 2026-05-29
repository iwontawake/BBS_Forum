package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.Response;

public interface FavoriteService {
    Response<Void> favorite(int userId, int postId);
    Response<Void> unfavorite(int userId, int postId);
}
