package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.BoardVO;
import com.jiang.bbs_forum.service.user.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    /** 获取所有板块 */
    @GetMapping
    public Response<List<BoardVO>> listBoards() {
        return boardService.listBoards();
    }

    /** 获取板块详情 */
    @GetMapping("/{id}")
    public Response<BoardVO> getBoardById(@PathVariable int id) {
        return boardService.getBoardById(id);
    }
}
