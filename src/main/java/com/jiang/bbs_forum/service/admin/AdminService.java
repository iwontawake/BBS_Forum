package com.jiang.bbs_forum.service.admin;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.CreateBoardRequest;
import com.jiang.bbs_forum.dto.request.UpdateBoardRequest;
import com.jiang.bbs_forum.dto.response.BoardVO;
import com.jiang.bbs_forum.dto.response.LogVO;
import com.jiang.bbs_forum.dto.response.StatusVO;
import com.jiang.bbs_forum.dto.response.UserVO;

public interface AdminService {
    Response<PageResponse<UserVO>> listUsers(String keyword, int page, int size);

    Response<StatusVO> updateUserStatus(int userId, int status);

    Response<PageResponse<LogVO>> getSystemLogs(int page, int size);

    Response<BoardVO> createBoard(CreateBoardRequest request, int adminId, String ip);

    Response<BoardVO> updateBoard(int boardId, UpdateBoardRequest request, int adminId, String ip);

    Response<Void> deleteBoard(int boardId, int adminId, String ip);

    Response<StatusVO> toggleTop(int postId, int isTop);

    Response<StatusVO> toggleEssence(int postId, int isEssence);
}
