package com.jiang.bbs_forum.controller.user;

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
public class CommentController {

    @Autowired
    private CommentService commentService;

    /** 获取帖子回复列表（含楼中楼） */
    @GetMapping("/posts/{postId}/comments")
    public Response<PageResponse<CommentVO>> listComments(
            @PathVariable int postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return commentService.listComments(postId, page, size);
    }

    /** 发布回复（一级回复或楼中楼） */
    @PostMapping("/comments")
    public Response<CommentVO> createComment(@RequestAttribute("userId") int userId,
                                              @Valid @RequestBody CreateCommentRequest request) {
        return commentService.createComment(userId, request);
    }

    /** 修改回复（仅作者本人） */
    @PutMapping("/comments/{id}")
    public Response<CommentVO> updateComment(@RequestAttribute("userId") int userId,
                                              @PathVariable("id") int commentId,
                                              @Valid @RequestBody UpdateCommentRequest request) {
        return commentService.updateComment(userId, commentId, request);
    }

    /** 删除回复（作者或管理员） */
    @DeleteMapping("/comments/{id}")
    public Response<Void> deleteComment(@RequestAttribute("userId") int userId,
                                         @PathVariable("id") int commentId) {
        return commentService.deleteComment(userId, commentId);
    }
}
