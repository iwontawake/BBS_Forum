package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.ChangePasswordRequest;
import com.jiang.bbs_forum.dto.request.UpdateProfileRequest;
import com.jiang.bbs_forum.dto.response.*;
import com.jiang.bbs_forum.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户完整信息
     */
    @GetMapping("/current")
    public Response<UserVO> getCurrentUser(@RequestAttribute("userId") int userId) {
        return userService.getCurrentUser(userId);
    }

    /**
     * 获取用户公开信息
     */
    @GetMapping("/{id}")
    public Response<UserVO> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * 修改个人资料
     */
    @PutMapping("/profile")
    public Response<ProfileVO> updateProfile(@RequestAttribute("userId") int userId,
                                             @Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateProfile(userId, request);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Response<Void> changePassword(@RequestAttribute("userId") int userId,
                                         @Valid @RequestBody ChangePasswordRequest request) {
        return userService.changePassword(userId, request);
    }

    /**
     * 获取我的积分记录（分页）
     */
    @GetMapping("/points")
    public Response<PageResponse<PointRecordVO>> getPointRecords(
            @RequestAttribute("userId") int userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getPointRecords(userId, page, size);
    }

    /**
     * 积分排行榜
     */
    @GetMapping("/points/rank")
    public Response<List<RankItemVO>> getPointsRank(@RequestParam(defaultValue = "10") int size) {
        return userService.getPointsRank(size);
    }

    /**
     * 获取我的帖子（分页）
     */
    @GetMapping("/posts")
    public Response<PageResponse<PostVO>> getMyPosts(
            @RequestAttribute("userId") int userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getMyPosts(userId, page, size);
    }

    /**
     * 获取我的回复（分页）
     */
    @GetMapping("/comments")
    public Response<PageResponse<CommentVO>> getMyComments(
            @RequestAttribute("userId") int userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getMyComments(userId, page, size);
    }

    /**
     * 获取我的收藏（分页）
     */
    @GetMapping("/favorites")
    public Response<PageResponse<PostVO>> getMyFavorites(
            @RequestAttribute("userId") int userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getMyFavorites(userId, page, size);
    }
}
