package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    /** 服务健康检查 */
    @GetMapping("/health")
    public Response<Map<String, String>> health() {
        return Response.success(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "version", "1.0.0"
        ));
    }
}
