package com.jiang.bbs_forum.controller.user;

import lombok.extern.slf4j.Slf4j;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.CreateCommentRequest;
import com.jiang.bbs_forum.dto.request.UpdateCommentRequest;
import com.jiang.bbs_forum.dto.response.CommentVO;
import com.jiang.bbs_forum.service.user.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取帖子回复列表（含楼中楼）
     */
    @GetMapping("/posts/{postId}/comments")
    public Response<PageResponse<CommentVO>> listComments(
            @PathVariable int postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("GET /api/posts/{}/comments - 获取回复列表", postId);
        return commentService.listComments(postId, page, size);
    }

    /**
     * 发布回复（一级回复或楼中楼）
     */
    @PostMapping("/comments")
    public Response<CommentVO> createComment(@RequestAttribute("userId") int userId,
                                             @Valid @RequestBody CreateCommentRequest request) {
        log.info("POST /api/comments - 发布回复, userId={}", userId);
        return commentService.createComment(userId, request);
    }

    /**
     * 修改回复（仅作者本人）
     */
    @PutMapping("/comments/{id}")
    public Response<CommentVO> updateComment(@RequestAttribute("userId") int userId,
                                             @PathVariable("id") int commentId,
                                             @Valid @RequestBody UpdateCommentRequest request) {
        log.info("PUT /api/comments/{} - 修改回复, userId={}", commentId, userId);
        return commentService.updateComment(userId, commentId, request);
    }

    /**
     * 删除回复（作者或管理员）
     */
    @DeleteMapping("/comments/{id}")
    public Response<Void> deleteComment(@RequestAttribute("userId") int userId,
                                        @PathVariable("id") int commentId) {
        log.info("DELETE /api/comments/{} - 删除回复, userId={}", commentId, userId);
        return commentService.deleteComment(userId, commentId);
    }
}
