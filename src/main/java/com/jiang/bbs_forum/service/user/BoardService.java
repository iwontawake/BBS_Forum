package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.BoardVO;

import java.util.List;

public interface BoardService {
    Response<List<BoardVO>> listBoards();

    Response<BoardVO> getBoardById(int id);
}
