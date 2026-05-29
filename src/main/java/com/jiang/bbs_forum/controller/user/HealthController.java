package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ApiResponse<?> health() {
        // TODO: 可扩展检查数据库连接状态
        return ApiResponse.success(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "version", "1.0.0"
        ));
    }
}
