package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.ChangePasswordRequest;
import com.jiang.bbs_forum.dto.request.UpdateProfileRequest;
import com.jiang.bbs_forum.dto.response.*;

import java.util.List;

public interface UserService {
    Response<UserVO> getCurrentUser(int userId);

    Response<UserVO> getUserById(int id);

    Response<ProfileVO> updateProfile(int userId, UpdateProfileRequest request);

    Response<Void> changePassword(int userId, ChangePasswordRequest request);

    Response<PageResponse<PointRecordVO>> getPointRecords(int userId, int page, int size);

    Response<List<RankItemVO>> getPointsRank(int size);

    Response<PageResponse<PostVO>> getMyPosts(int userId, int page, int size);

    Response<PageResponse<CommentVO>> getMyComments(int userId, int page, int size);

    Response<PageResponse<PostVO>> getMyFavorites(int userId, int page, int size);
}
