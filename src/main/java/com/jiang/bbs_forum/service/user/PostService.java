package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.CreatePostRequest;
import com.jiang.bbs_forum.dto.request.UpdatePostRequest;
import com.jiang.bbs_forum.dto.response.PostVO;

import java.util.List;

public interface PostService {
    Response<PageResponse<PostVO>> listPosts(Integer boardId, String keyword, int page, int size, String orderBy);

    Response<List<PostVO>> getHotPosts(int size);

    Response<PostVO> getPostById(int id);

    Response<PostVO> createPost(int userId, CreatePostRequest request);

    Response<PostVO> updatePost(int userId, int postId, UpdatePostRequest request);

    Response<Void> deletePost(int userId, int postId);
}
