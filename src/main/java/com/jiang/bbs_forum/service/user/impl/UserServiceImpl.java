package com.jiang.bbs_forum.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.ChangePasswordRequest;
import com.jiang.bbs_forum.dto.request.UpdateProfileRequest;
import com.jiang.bbs_forum.dto.response.*;
import com.jiang.bbs_forum.entity.User;
import com.jiang.bbs_forum.entity.UserProfile;
import com.jiang.bbs_forum.mapper.*;
import com.jiang.bbs_forum.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private PointRecordMapper pointRecordMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==================== A模块：资料相关 ====================

    @Override
    public Response<UserVO> getCurrentUser(int userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Response.error(404, "用户不存在");
        }

        UserProfile profile = userProfileMapper.selectOne(
                new LambdaQueryWrapper<UserProfile>().eq(UserProfile::getUserId, userId));

        UserVO vo = UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .points(user.getPoints())
                .lastLoginTime(user.getLastLoginTime() != null ? user.getLastLoginTime().format(DTF) : null)
                .loginCount(user.getLoginCount())
                .profile(buildProfile(profile))
                .build();

        return Response.success(vo);
    }

    @Override
    public Response<UserVO> getUserById(int id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Response.error(404, "用户不存在");
        }

        UserProfile profile = userProfileMapper.selectOne(
                new LambdaQueryWrapper<UserProfile>().eq(UserProfile::getUserId, id));

        UserVO vo = UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .points(user.getPoints())
                .profile(profile != null ? ProfileVO.builder()
                        .nickname(profile.getNickname())
                        .avatar(profile.getAvatar())
                        .workNature(profile.getWorkNature())
                        .workLocation(profile.getWorkLocation())
                        .signature(profile.getSignature())
                        .build() : null)
                .build();

        return Response.success(vo);
    }

    @Override
    public Response<ProfileVO> updateProfile(int userId, UpdateProfileRequest request) {
        // 查现有 profile，不存在则创建
        UserProfile profile = userProfileMapper.selectOne(
                new LambdaQueryWrapper<UserProfile>().eq(UserProfile::getUserId, userId));
        if (profile == null) {
            profile = new UserProfile();
            profile.setUserId(userId);
        }

        if (request.getNickname() != null) profile.setNickname(request.getNickname());
        if (request.getPhone() != null) profile.setPhone(request.getPhone());
        if (request.getWorkNature() != null) profile.setWorkNature(request.getWorkNature());
        if (request.getWorkLocation() != null) profile.setWorkLocation(request.getWorkLocation());
        if (request.getSignature() != null) profile.setSignature(request.getSignature());

        userProfileMapper.insertOrUpdate(profile);

        ProfileVO vo = ProfileVO.builder()
                .nickname(profile.getNickname())
                .avatar(profile.getAvatar())
                .phone(profile.getPhone())
                .workNature(profile.getWorkNature())
                .workLocation(profile.getWorkLocation())
                .signature(profile.getSignature())
                .build();

        return Response.success("修改成功", vo);
    }

    @Override
    public Response<Void> changePassword(int userId, ChangePasswordRequest request) {
        User user = userMapper.selectById(userId);
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return Response.error(400, "原密码错误");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userMapper.updateById(user);

        return Response.success("密码修改成功", null);
    }

    // ==================== D模块：积分收藏相关（TODO） ====================

    @Override
    public Response<PageResponse<PointRecordVO>> getPointRecords(int userId, int page, int size) {
        // TODO: 分页查询 point_records 表，按时间倒序
        return null;
    }

    @Override
    public Response<List<RankItemVO>> getPointsRank(int size) {
        // TODO: 按积分降序取前N名
        return null;
    }

    @Override
    public Response<PageResponse<PostVO>> getMyPosts(int userId, int page, int size) {
        // TODO: 分页查询我的帖子
        return null;
    }

    @Override
    public Response<PageResponse<CommentVO>> getMyComments(int userId, int page, int size) {
        // TODO: 分页查询我的回复
        return null;
    }

    @Override
    public Response<PageResponse<PostVO>> getMyFavorites(int userId, int page, int size) {
        // TODO: 分页查询我的收藏
        return null;
    }

    // ==================== 工具方法 ====================

    private ProfileVO buildProfile(UserProfile p) {
        if (p == null) return null;
        return ProfileVO.builder()
                .nickname(p.getNickname())
                .avatar(p.getAvatar())
                .phone(p.getPhone())
                .workNature(p.getWorkNature())
                .workLocation(p.getWorkLocation())
                .signature(p.getSignature())
                .build();
    }
}
