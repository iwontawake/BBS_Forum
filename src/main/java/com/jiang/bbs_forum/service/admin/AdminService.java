package com.jiang.bbs_forum.service.admin;

import com.jiang.bbs_forum.common.ApiResponse;
import com.jiang.bbs_forum.dto.request.CreateBoardRequest;
import com.jiang.bbs_forum.dto.request.UpdateBoardRequest;

public interface AdminService {
    ApiResponse<?> listUsers(String keyword, int page, int size);
    ApiResponse<?> updateUserStatus(int userId, int status);
    ApiResponse<?> getSystemLogs(int page, int size);
    ApiResponse<?> createBoard(CreateBoardRequest request, int adminId, String ip);
    ApiResponse<?> updateBoard(int boardId, UpdateBoardRequest request, int adminId, String ip);
    ApiResponse<?> deleteBoard(int boardId, int adminId, String ip);
    ApiResponse<?> toggleTop(int postId, int isTop);
    ApiResponse<?> toggleEssence(int postId, int isEssence);
}
