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
        User user = userMapper.selectById(userId);
        int balance = user.getPoints() + points;

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPoints(balance);
        userMapper.updateById(updateUser);

        PointRecord record = new PointRecord();
        record.setUserId(userId);
        record.setType(1);
        record.setReason(reason);
        record.setPoints(points);
        record.setBalance(balance);
        pointRecordMapper.insert(record);
    }

    @Override
    @Transactional
    public void consumePoints(int userId, int points, String reason) {
        User user = userMapper.selectById(userId);
        int balance = user.getPoints() - points;

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPoints(balance);
        userMapper.updateById(updateUser);

        PointRecord record = new PointRecord();
        record.setUserId(userId);
        record.setType(2);
        record.setReason(reason);
        record.setPoints(-points);
        record.setBalance(balance);
        pointRecordMapper.insert(record);
    }
}
