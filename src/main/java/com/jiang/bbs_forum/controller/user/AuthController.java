package com.jiang.bbs_forum.controller.user;

import lombok.extern.slf4j.Slf4j;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.LoginRequest;
import com.jiang.bbs_forum.dto.request.RegisterRequest;
import com.jiang.bbs_forum.dto.response.LoginVO;
import com.jiang.bbs_forum.service.user.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Response<LoginVO> register(@Valid @RequestBody RegisterRequest request) {
        log.info("POST /api/auth/register - 用户注册");
        return authService.register(request);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Response<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        log.info("POST /api/auth/login - 用户登录");
        return authService.login(request);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Response<Void> logout(@RequestAttribute("userId") int userId) {
        log.info("POST /api/auth/logout - 退出登录");
        return authService.logout(userId);
    }
}
