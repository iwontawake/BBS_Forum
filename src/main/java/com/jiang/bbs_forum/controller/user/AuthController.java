package com.jiang.bbs_forum.controller.user;

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
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Response<LoginVO> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Response<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Response<Void> logout(@RequestAttribute("userId") int userId) {
        return authService.logout(userId);
    }
}
