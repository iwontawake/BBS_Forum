package com.jiang.bbs_forum.service.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.LoginRequest;
import com.jiang.bbs_forum.dto.request.RegisterRequest;
import com.jiang.bbs_forum.dto.response.LoginVO;

public interface AuthService {
    Response<LoginVO> register(RegisterRequest request);
    Response<LoginVO> login(LoginRequest request);
    Response<Void> logout(int userId);
}
