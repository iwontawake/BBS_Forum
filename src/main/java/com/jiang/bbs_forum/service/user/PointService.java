package com.jiang.bbs_forum.service.user;

public interface PointService {
    void addPoints(int userId, int points, String reason);

    void consumePoints(int userId, int points, String reason);
}
