package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;
import com.jiang.bbs_forum.dto.request.CreateCommentRequest;
import com.jiang.bbs_forum.dto.request.UpdateCommentRequest;

public interface CommentService {
    ApiResponse<?> listComments(int postId, int page, int size);
    ApiResponse<?> createComment(int userId, CreateCommentRequest request);
    ApiResponse<?> updateComment(int userId, int commentId, UpdateCommentRequest request);
    ApiResponse<?> deleteComment(int userId, int commentId);
}
