package com.jiang.bbs_forum.controller.user;

import lombok.extern.slf4j.Slf4j;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.BoardVO;
import com.jiang.bbs_forum.service.user.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    /**
     * 获取所有板块
     */
    @GetMapping
    public Response<List<BoardVO>> listBoards() {
        log.info("GET /api/boards - 获取所有板块");
        return boardService.listBoards();
    }

    /**
     * 根据板块ID获取详情
     */
    @GetMapping("/{id}")
    public Response<BoardVO> getBoardById(@PathVariable int id) {
        log.info("GET /api/boards/{} - 根据板块ID获取详情", id);
        return boardService.getBoardById(id);
    }
}
