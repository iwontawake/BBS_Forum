package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.CreatePostRequest;
import com.jiang.bbs_forum.dto.request.UpdatePostRequest;
import com.jiang.bbs_forum.dto.response.PostVO;
import com.jiang.bbs_forum.service.user.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /** 获取帖子列表（支持按板块、关键词筛选和排序） */
    @GetMapping
    public Response<PageResponse<PostVO>> listPosts(
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String orderBy) {
        return postService.listPosts(boardId, keyword, page, size, orderBy);
    }

    /** 获取热门帖子（近7天热度排行） */
    @GetMapping("/hot")
    public Response<List<PostVO>> getHotPosts(@RequestParam(defaultValue = "10") int size) {
        return postService.getHotPosts(size);
    }

    /** 获取帖子详情（浏览量+1） */
    @GetMapping("/{id}")
    public Response<PostVO> getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    /** 发布帖子（普通帖/需求帖） */
    @PostMapping
    public Response<PostVO> createPost(@RequestAttribute("userId") int userId,
                                        @Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(userId, request);
    }

    /** 修改帖子（仅作者本人） */
    @PutMapping("/{id}")
    public Response<PostVO> updatePost(@RequestAttribute("userId") int userId,
                                        @PathVariable("id") int postId,
                                        @Valid @RequestBody UpdatePostRequest request) {
        return postService.updatePost(userId, postId, request);
    }

    /** 删除帖子（作者或管理员） */
    @DeleteMapping("/{id}")
    public Response<Void> deletePost(@RequestAttribute("userId") int userId,
                                      @PathVariable("id") int postId) {
        return postService.deletePost(userId, postId);
    }
}
