package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;
import com.jiang.bbs_forum.dto.request.CreatePostRequest;
import com.jiang.bbs_forum.dto.request.UpdatePostRequest;

public interface PostService {
    ApiResponse<?> listPosts(Integer boardId, String keyword, int page, int size, String orderBy);
    ApiResponse<?> getHotPosts(int size);
    ApiResponse<?> getPostById(int id);
    ApiResponse<?> createPost(int userId, CreatePostRequest request);
    ApiResponse<?> updatePost(int userId, int postId, UpdatePostRequest request);
    ApiResponse<?> deletePost(int userId, int postId);
}
