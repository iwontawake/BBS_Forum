package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.NotificationRequest;
import com.jiang.bbs_forum.service.user.NotificationService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Resource
    private NotificationService notificationService;


    /**
     * 点赞通知
     */
    @PostMapping("/like")
    public Response<Void> like(@RequestBody NotificationRequest req) {
        notificationService.notifyLike(
                req.getFromUserId(),
                req.getToUserId(),
                req.getTargetType(),
                req.getTargetId()
        );
        return Response.success(null);
    }

    /**
     * 评论通知
     */
    @PostMapping("/comment")
    public Response<Void> comment(@RequestBody NotificationRequest req) {
        notificationService.notifyComment(
                req.getFromUserId(),
                req.getToUserId(),
                req.getTargetType(),
                req.getTargetId(),
                req.getContent()
        );
        return Response.success(null);
    }

    /**
     * 收藏通知
     */
    @PostMapping("/favorite")
    public Response<Void> favorite(@RequestBody NotificationRequest req) {
        notificationService.notifyFavorite(
                req.getFromUserId(),
                req.getToUserId(),
                req.getTargetId()
        );
        return Response.success(null);
    }

    /**
     * @用户通知
     */
    @PostMapping("/at")
    public Response<Void> at(@RequestBody NotificationRequest req) {
        notificationService.notifyAt(
                req.getFromUserId(),
                req.getToUserId(),
                req.getTargetType(),
                req.getTargetId()
        );
        return Response.success(null);
    }

    /**
     * 未读数量
     */
    @GetMapping("/unreadCount")
    public Response<Long> getUnreadCount(@RequestParam int userId) {
        return notificationService.getUnreadCount(userId);
    }

    /**
     * 通知列表
     */
    @GetMapping("/list")
    public Response<?> list(@RequestParam int userId,
                            @RequestParam int page,
                            @RequestParam int size) {
        return notificationService.list(userId, page, size);
    }

    /**
     * 标记已读
     */
    @PutMapping("/read/{id}")
    public Response<Void> read(@PathVariable int id) {
        return notificationService.markRead(id);
    }

    /**
     * 全部已读
     */
    @PutMapping("/read/all/{userId}")
    public Response<Void> readAll(@PathVariable int userId) {
        return notificationService.markAllRead(userId);
    }
}