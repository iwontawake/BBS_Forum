package com.jiang.bbs_forum.controller.user;

import lombok.extern.slf4j.Slf4j;

import com.jiang.bbs_forum.common.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class HealthController {

    /**
     * 服务健康检查
     */
    @GetMapping("/health")
    public Response<Map<String, String>> health() {
        log.info("GET /api/health - 健康检查");
        return Response.success(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "version", "1.0.0"
        ));
    }
}
