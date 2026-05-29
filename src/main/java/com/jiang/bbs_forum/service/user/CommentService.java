package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.CreateCommentRequest;
import com.jiang.bbs_forum.dto.request.UpdateCommentRequest;
import com.jiang.bbs_forum.dto.response.CommentVO;

public interface CommentService {
    Response<PageResponse<CommentVO>> listComments(int postId, int page, int size);
    Response<CommentVO> createComment(int userId, CreateCommentRequest request);
    Response<CommentVO> updateComment(int userId, int commentId, UpdateCommentRequest request);
    Response<Void> deleteComment(int userId, int commentId);
}
