package com.jiang.bbs_forum.service.user.impl;

import com.jiang.bbs_forum.entity.PointRecord;
import com.jiang.bbs_forum.entity.User;
import com.jiang.bbs_forum.mapper.PointRecordMapper;
import com.jiang.bbs_forum.mapper.UserMapper;
import com.jiang.bbs_forum.service.user.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRecordMapper pointRecordMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void addPoints(int userId, int points, String reason) {
        // TODO: 1. 增加用户积分
        // TODO: 2. 插入积分记录（type=1获得，points=正数，balance=变动后余额）
    }

    @Override
    @Transactional
    public void consumePoints(int userId, int points, String reason) {
        // TODO: 1. 扣减用户积分
        // TODO: 2. 插入积分记录（type=2消耗，points=负数，balance=变动后余额）
    }
}
