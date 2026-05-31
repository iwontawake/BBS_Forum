package com.jiang.bbs_forum.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.ChangePasswordRequest;
import com.jiang.bbs_forum.dto.request.UpdateProfileRequest;
import com.jiang.bbs_forum.dto.response.*;
import com.jiang.bbs_forum.entity.*;
import com.jiang.bbs_forum.mapper.*;
import com.jiang.bbs_forum.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private LikeMapper likeMapper;

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

    //分页查询我的帖子
    @Override
    public Response<PageResponse<PostVO>> getMyPosts(int userId, int page, int size) {

        Page<Post> p = new Page<>(page, size);

        IPage<Post> result = postMapper.selectPage(
                p,
                new LambdaQueryWrapper<Post>()
                        .eq(Post::getUserId, userId)
                        .orderByDesc(Post::getCreateTime)
        );

        List<Post> posts = result.getRecords();

        if (posts.isEmpty()) {
            return Response.success(new PageResponse<>(0L, List.of(), page, size));
        }

        //批量预查询

        List<Integer> postIds = posts.stream()
                .map(Post::getId)
                .toList();

        // 1. 查当前用户点赞过哪些帖子
        List<Integer> likedIds = likeMapper.selectObjs(
                new LambdaQueryWrapper<Like>()
                        .select(Like::getTargetId)
                        .eq(Like::getUserId, userId)
                        .eq(Like::getTargetType, 1)
                        .in(Like::getTargetId, postIds)
        ).stream().map(o -> (Integer) o).toList();

        // 2. 查当前用户收藏过哪些帖子
        List<Integer> favoritedIds = favoriteMapper.selectObjs(
                new LambdaQueryWrapper<Favorite>()
                        .select(Favorite::getPostId)
                        .eq(Favorite::getUserId, userId)
                        .in(Favorite::getPostId, postIds)
        ).stream().map(o -> (Integer) o).toList();

        // 转 Set 提速 O(1)
        Set<Integer> likedSet = likedIds.stream().collect(java.util.stream.Collectors.toSet());
        Set<Integer> favSet = favoritedIds.stream().collect(java.util.stream.Collectors.toSet());

        // VO 构建
        List<PostVO> list = posts.stream().map(post -> {

            PostVO vo = new PostVO();
            vo.setId(post.getId());
            vo.setTitle(post.getTitle());
            vo.setContent(post.getContent());
            vo.setLikeCount(post.getLikeCount());
            vo.setCommentCount(post.getCommentCount());

            vo.setLiked(likedSet.contains(post.getId()));
            vo.setFavorited(favSet.contains(post.getId()));

            vo.setCreateTime(post.getCreateTime() != null
                    ? post.getCreateTime().format(DTF)
                    : null);

            return vo;
        }).toList();

        return Response.success(
                new PageResponse<>(result.getTotal(), list, page, size)
        );
    }

    // 分页查询我的回复
    @Override
    public Response<PageResponse<CommentVO>> getMyComments(int userId, int page, int size) {

        Page<Comment> p = new Page<>(page, size);

        IPage<Comment> result = commentMapper.selectPage(
                p,
                new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getUserId, userId)
                        .orderByDesc(Comment::getCreateTime)
        );

        List<CommentVO> list = result.getRecords().stream().map(c -> {

            CommentVO vo = new CommentVO();
            vo.setId(c.getId());
            vo.setPostId(c.getPostId());
            vo.setContent(c.getContent());
            vo.setCreateTime(c.getCreateTime() != null ? c.getCreateTime().format(DTF) : null);

            return vo;
        }).toList();

        return Response.success(
                new PageResponse<>(result.getTotal(), list, page, size)
        );
    }

    //分页查询我的收藏
    @Override
    public Response<PageResponse<PostVO>> getMyFavorites(int userId, int page, int size) {

        int offset = (page - 1) * size;

        List<Integer> postIds = favoriteMapper.selectObjs(
                new LambdaQueryWrapper<Favorite>()
                        .select(Favorite::getPostId)
                        .eq(Favorite::getUserId, userId)
                        .orderByDesc(Favorite::getCreateTime)
                        .last("limit " + offset + "," + size)
        ).stream().map(o -> (Integer) o).toList();

        if (postIds.isEmpty()) {
            return Response.success(new PageResponse<>(0L, List.of(), page, size));
        }

        List<Post> posts = postMapper.selectBatchIds(postIds);

        List<PostVO> list = posts.stream().map(post -> {

            PostVO vo = new PostVO();

            vo.setId(post.getId());
            vo.setTitle(post.getTitle());
            vo.setContent(post.getContent());
            vo.setLikeCount(post.getLikeCount());
            vo.setCommentCount(post.getCommentCount());
            vo.setFavorited(true);

            vo.setLiked(
                    likeMapper.selectCount(
                            new LambdaQueryWrapper<Like>()
                                    .eq(Like::getUserId, userId)
                                    .eq(Like::getTargetType, 1)
                                    .eq(Like::getTargetId, post.getId())
                    ) > 0
            );

            vo.setFavorited(true);
            vo.setLiked(false);

            vo.setCreateTime(post.getCreateTime() != null ? post.getCreateTime().format(DTF) : null);

            return vo;
        }).toList();

        Long total = favoriteMapper.selectCount(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
        );

        return Response.success(
                new PageResponse<>(total, list, page, size)
        );
    }

    //分页查询我的点赞
    @Override
    public Response<PageResponse<PostVO>> getMyLikes(int userId, int page, int size) {

        int offset = (page - 1) * size;

        List<Integer> postIds = likeMapper.selectObjs(
                new LambdaQueryWrapper<Like>()
                        .select(Like::getTargetId)
                        .eq(Like::getUserId, userId)
                        .eq(Like::getTargetType, 1)
                        .orderByDesc(Like::getCreateTime)
                        .last("limit " + offset + "," + size)
        ).stream().map(o -> (Integer) o).toList();

        if (postIds.isEmpty()) {
            return Response.success(new PageResponse<>(0L, List.of(), page, size));
        }

        List<Post> posts = postMapper.selectBatchIds(postIds);

        List<PostVO> list = posts.stream().map(post -> {

            PostVO vo = new PostVO();

            vo.setId(post.getId());
            vo.setTitle(post.getTitle());
            vo.setContent(post.getContent());
            vo.setLikeCount(post.getLikeCount());
            vo.setCommentCount(post.getCommentCount());

            vo.setLiked(true);

            vo.setFavorited(
                    favoriteMapper.selectCount(
                            new LambdaQueryWrapper<Favorite>()
                                    .eq(Favorite::getUserId, userId)
                                    .eq(Favorite::getPostId, post.getId())
                    ) > 0
            );

            vo.setCreateTime(post.getCreateTime() != null ? post.getCreateTime().format(DTF) : null);

            return vo;
        }).toList();

        Long total = likeMapper.selectCount(
                new LambdaQueryWrapper<Like>()
                        .eq(Like::getUserId, userId)
                        .eq(Like::getTargetType, 1)
        );

        return Response.success(
                new PageResponse<>(total, list, page, size)
        );
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
