package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;

public interface BoardService {
    ApiResponse<?> listBoards();
    ApiResponse<?> getBoardById(int id);
}
