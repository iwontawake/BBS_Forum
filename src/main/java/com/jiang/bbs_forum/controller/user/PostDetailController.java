package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.PostDetailVO;
import com.jiang.bbs_forum.service.user.PostDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-detail")
@Slf4j
public class PostDetailController {

    @Autowired
    private PostDetailService postDetailService;

    /**
     * 获取帖子详情（包含评论树和点赞状态）
     */
    @GetMapping("/{id}")
    public Response<PostDetailVO> getPostDetail(
            @RequestAttribute(value = "userId", required = false) Integer userId,
            @PathVariable("id") int postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/post-detail/{} userId={}", postId, userId);
        return postDetailService.getPostDetail(userId, postId, page, size);
    }
}
