package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.ApiResponse;
import com.jiang.bbs_forum.dto.request.LoginRequest;
import com.jiang.bbs_forum.dto.request.RegisterRequest;

public interface AuthService {
    ApiResponse<?> register(RegisterRequest request);
    ApiResponse<?> login(LoginRequest request);
    ApiResponse<?> logout(int userId);
}
