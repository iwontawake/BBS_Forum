package com.jiang.bbs_forum.controller.admin;

import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.*;
import com.jiang.bbs_forum.dto.response.BoardVO;
import com.jiang.bbs_forum.dto.response.LogVO;
import com.jiang.bbs_forum.dto.response.StatusVO;
import com.jiang.bbs_forum.dto.response.UserVO;
import com.jiang.bbs_forum.service.admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ==================== 用户管理 ====================

    /** 获取用户列表（分页，支持关键词搜索） */
    @GetMapping("/users")
    public Response<PageResponse<UserVO>> listUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return adminService.listUsers(keyword, page, size);
    }

    /** 禁用/启用用户 */
    @PutMapping("/users/{id}/status")
    public Response<StatusVO> updateUserStatus(
            @PathVariable("id") int userId,
            @Valid @RequestBody UpdateUserStatusRequest request) {
        return adminService.updateUserStatus(userId, request.getStatus());
    }

    // ==================== 板块管理 ====================

    /** 新增板块 */
    @PostMapping("/boards")
    public Response<BoardVO> createBoard(@Valid @RequestBody CreateBoardRequest request,
                                          @RequestAttribute("userId") int adminId,
                                          HttpServletRequest httpRequest) {
        return adminService.createBoard(request, adminId, httpRequest.getRemoteAddr());
    }

    /** 修改板块 */
    @PutMapping("/boards/{id}")
    public Response<BoardVO> updateBoard(@PathVariable("id") int boardId,
                                          @Valid @RequestBody UpdateBoardRequest request,
                                          @RequestAttribute("userId") int adminId,
                                          HttpServletRequest httpRequest) {
        return adminService.updateBoard(boardId, request, adminId, httpRequest.getRemoteAddr());
    }

    /** 删除板块 */
    @DeleteMapping("/boards/{id}")
    public Response<Void> deleteBoard(@PathVariable("id") int boardId,
                                       @RequestAttribute("userId") int adminId,
                                       HttpServletRequest httpRequest) {
        return adminService.deleteBoard(boardId, adminId, httpRequest.getRemoteAddr());
    }

    // ==================== 帖子管理 ====================

    /** 帖子置顶/取消置顶 */
    @PutMapping("/posts/{id}/top")
    public Response<StatusVO> toggleTop(@PathVariable("id") int postId,
                                         @RequestBody TopRequest request) {
        return adminService.toggleTop(postId, request.getIsTop());
    }

    /** 帖子加精/取消加精 */
    @PutMapping("/posts/{id}/essence")
    public Response<StatusVO> toggleEssence(@PathVariable("id") int postId,
                                             @RequestBody EssenceRequest request) {
        return adminService.toggleEssence(postId, request.getIsEssence());
    }

    // ==================== 系统日志 ====================

    /** 获取系统操作日志（分页） */
    @GetMapping("/logs")
    public Response<PageResponse<LogVO>> getSystemLogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return adminService.getSystemLogs(page, size);
    }
}
