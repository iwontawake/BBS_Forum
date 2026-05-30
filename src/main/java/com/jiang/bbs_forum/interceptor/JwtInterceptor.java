package com.jiang.bbs_forum.interceptor;

import com.jiang.bbs_forum.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    /** 不需要登录就能访问的接口 */
    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/api/health",
            "/api/auth/login",
            "/api/auth/register"
    );

    /** 只对 GET 请求公开的前缀 */
    private static final String[] GET_PUBLIC_PREFIXES = {
            "/api/boards",
            "/api/posts",
            "/api/users/points/rank"
    };

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String path = request.getRequestURI();
        String method = request.getMethod();

        log.info(">>> 拦截器收到请求: {} {}", method, path);

        if (isPublicPath(path, method)) {
            log.info(">>> 公开接口，放行: {} {}", method, path);
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        log.info(">>> Authorization头: {}", authHeader);

        if (authHeader == null) {
            log.warn(">>> 未登录 [{} {}], 未提供Authorization头", method, path);
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\",\"data\":null}");
            return false;
        }

        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        if (!jwtUtils.validateToken(token)) {
            log.warn(">>> token校验失败 [{} {}]", method, path);
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"token无效或已过期\",\"data\":null}");
            return false;
        }

        log.info(">>> token校验通过, userId={}", jwtUtils.getUserId(token));
        request.setAttribute("userId", jwtUtils.getUserId(token));
        request.setAttribute("username", jwtUtils.parseToken(token).get("username"));
        request.setAttribute("role", jwtUtils.parseToken(token).get("role"));
        return true;
    }

    private boolean isPublicPath(String path, String method) {
        // 完全匹配的公开路径
        if (PUBLIC_PATHS.contains(path)) {
            return true;
        }
        // GET 请求的公开前缀
        if ("GET".equalsIgnoreCase(method)) {
            for (String prefix : GET_PUBLIC_PREFIXES) {
                if (path.startsWith(prefix)) {
                    return true;
                }
            }
            // GET /api/users/{id} 也是公开的
            if (path.matches("/api/users/\\d+")) {
                return true;
            }
        }
        return false;
    }
}
