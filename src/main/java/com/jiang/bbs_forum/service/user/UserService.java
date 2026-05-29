package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;
import com.jiang.bbs_forum.dto.request.ChangePasswordRequest;
import com.jiang.bbs_forum.dto.request.UpdateProfileRequest;

public interface UserService {
    ApiResponse<?> getCurrentUser(int userId);
    ApiResponse<?> getUserById(int id);
    ApiResponse<?> updateProfile(int userId, UpdateProfileRequest request);
    ApiResponse<?> changePassword(int userId, ChangePasswordRequest request);
    ApiResponse<?> getPointRecords(int userId, int page, int size);
    ApiResponse<?> getPointsRank(int size);
    ApiResponse<?> getMyPosts(int userId, int page, int size);
    ApiResponse<?> getMyComments(int userId, int page, int size);
    ApiResponse<?> getMyFavorites(int userId, int page, int size);
}
