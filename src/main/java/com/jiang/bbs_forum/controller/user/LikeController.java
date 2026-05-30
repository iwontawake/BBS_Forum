package com.jiang.bbs_forum.controller.user;

import lombok.extern.slf4j.Slf4j;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.LikeRequest;
import com.jiang.bbs_forum.service.user.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@Slf4j
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 点赞（帖子或回复）
     */
    @PostMapping
    public Response<Void> like(@RequestAttribute("userId") int userId,
                               @Valid @RequestBody LikeRequest request) {
        log.info("POST /api/likes - 点赞, userId={}", userId);
        return likeService.like(userId, request.getTargetType(), request.getTargetId());
    }

    /**
     * 取消点赞
     */
    @DeleteMapping
    public Response<Void> unlike(@RequestAttribute("userId") int userId,
                                 @RequestParam int targetType,
                                 @RequestParam int targetId) {
        log.info("DELETE /api/likes - 取消点赞, userId={}", userId);
        return likeService.unlike(userId, targetType, targetId);
    }
}
