package com.jiang.bbs_forum.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.CreateCommentRequest;
import com.jiang.bbs_forum.dto.request.UpdateCommentRequest;
import com.jiang.bbs_forum.dto.response.CommentVO;
import com.jiang.bbs_forum.entity.Comment;
import com.jiang.bbs_forum.entity.Post;
import com.jiang.bbs_forum.entity.User;
import com.jiang.bbs_forum.mapper.CommentMapper;
import com.jiang.bbs_forum.mapper.PostMapper;
import com.jiang.bbs_forum.mapper.UserMapper;
import com.jiang.bbs_forum.service.user.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jiang.bbs_forum.dto.response.AtUserVO;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<PageResponse<CommentVO>> listComments(int postId, int page, int size) {

        // 查询当前帖子所有未删除评论
        List<Comment> comments = commentMapper.selectList(
                new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getPostId, postId)
                        .eq(Comment::getIsDeleted, 0)
                        .orderByDesc(Comment::getCreateTime)
        );

        if (comments.isEmpty()) {
            PageResponse<CommentVO> empty = new PageResponse<>();
            empty.setList(new ArrayList<>());
            empty.setTotal(0L);
            empty.setPage(page);
            empty.setSize(size);
            return Response.success(empty);
        }

        // 批量查询用户信息，避免N+1查询
        List<Integer> userIds = comments.stream()
                .map(Comment::getUserId)
                .distinct()
                .collect(Collectors.toList());

        Map<Integer, User> userMap = userMapper.selectBatchIds(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        Map<Integer, CommentVO> voMap = new HashMap<>();
        List<CommentVO> allVO = new ArrayList<>();

        // 转换为VO并填充用户信息
        for (Comment c : comments) {

            User user = userMap.get(c.getUserId());

            CommentVO vo = new CommentVO();
            vo.setId(c.getId());
            vo.setPostId(c.getPostId());
            vo.setParentId(c.getParentId());
            vo.setContent(c.getContent());
            vo.setUserId(c.getUserId());

            vo.setUsername(user == null ? null : user.getUsername());
            vo.setNickname(user == null ? null : user.getNickname());
            vo.setAvatar(user == null ? "default.png" : user.getAvatar());

            vo.setLikeCount(c.getLikeCount());

            vo.setCreateTime(
                    c.getCreateTime() == null ? null :
                            c.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );

            vo.setChildren(new ArrayList<>());

            String atNames = c.getAtUsernames();

            if (atNames != null && !atNames.isBlank()) {

                List<String> usernames =
                        Arrays.stream(atNames.split(","))
                                .toList();

                List<User> atUsers = userMapper.selectList(
                        new LambdaQueryWrapper<User>()
                                .in(User::getUsername, usernames)
                );

                List<AtUserVO> atUserVOList = atUsers.stream().map(u -> {
                    AtUserVO item = new AtUserVO();
                    item.setUserId(u.getId());
                    item.setUsername(u.getUsername());
                    return item;
                }).toList();

                vo.setAtUsers(atUserVOList);
            }

            voMap.put(vo.getId(), vo);
            allVO.add(vo);
        }

        // 构建评论树结构
        List<CommentVO> roots = new ArrayList<>();

        for (CommentVO vo : allVO) {
            if (vo.getParentId() == null) {
                roots.add(vo);
            } else {
                CommentVO parent = voMap.get(vo.getParentId());
                if (parent != null) {
                    parent.getChildren().add(vo);
                }
            }
        }

        int fromIndex = Math.min((page - 1) * size, roots.size());
        int toIndex = Math.min(fromIndex + size, roots.size());

        List<CommentVO> pageList = roots.subList(fromIndex, toIndex);

        PageResponse<CommentVO> result = new PageResponse<>();
        result.setList(pageList);
        result.setTotal((long) roots.size());
        result.setPage(page);
        result.setSize(size);

        return Response.success(result);
    }

    @Override
    public Response<CommentVO> createComment(int userId, CreateCommentRequest request) {

        // 1. 校验帖子是否存在
        Post post = postMapper.selectById(request.getPostId());
        if (post == null) {
            return Response.error(404, "帖子不存在");
        }

        // 2. 校验父评论（楼中楼）
        if (request.getParentId() != null) {
            Comment parent = commentMapper.selectById(request.getParentId());
            if (parent == null) {
                return Response.error(404, "父评论不存在");
            }
        }

        // 3. 构建评论实体
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(request.getPostId());
        comment.setParentId(request.getParentId());
        comment.setContent(request.getContent());
        comment.setLikeCount(0);

        // 4. 解析@用户
        List<String> atUsernames = extractAtUsernames(request.getContent());

        List<User> atUsers = new ArrayList<>();
        List<String> invalidUsers = new ArrayList<>();

        if (!atUsernames.isEmpty()) {

            atUsers = userMapper.selectList(
                    new LambdaQueryWrapper<User>()
                            .in(User::getUsername, atUsernames)
            );

            Set<String> existNames = atUsers.stream()
                    .map(User::getUsername)
                    .collect(Collectors.toSet());

            invalidUsers = atUsernames.stream()
                    .filter(name -> !existNames.contains(name))
                    .collect(Collectors.toList());

            comment.setAtUsernames(String.join(",", existNames));
        }

        // 5. 插入评论
        commentMapper.insert(comment);

        // 6. 更新帖子评论数
        Post update = new Post();
        update.setId(post.getId());
        update.setCommentCount(
                post.getCommentCount() == null ? 1 : post.getCommentCount() + 1
        );
        postMapper.updateById(update);

        // 7. 当前用户信息
        User user = userMapper.selectById(userId);

        // 8. 构建 VO
        CommentVO vo = new CommentVO();
        vo.setId(comment.getId());
        vo.setPostId(comment.getPostId());
        vo.setParentId(comment.getParentId());
        vo.setContent(comment.getContent());
        vo.setUserId(comment.getUserId());

        vo.setUsername(user == null ? null : user.getUsername());
        vo.setNickname(user == null ? null : user.getNickname());
        vo.setAvatar(user == null ? "default.png" : user.getAvatar());

        vo.setLikeCount(0);

        vo.setCreateTime(
                comment.getCreateTime() == null ? null :
                        comment.getCreateTime().format(
                                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        )
        );

        vo.setChildren(new java.util.ArrayList<>());

        // 9. @用户返回结构（有效用户）
        List<AtUserVO> atUserVOList = atUsers.stream().map(u -> {
            AtUserVO item = new AtUserVO();
            item.setUserId(u.getId());
            item.setUsername(u.getUsername());
            return item;
        }).collect(Collectors.toList());

        vo.setAtUsers(atUserVOList);
        vo.setInvalidAtUsers(invalidUsers);

        String msg = invalidUsers.isEmpty()
                ? "评论成功"
                : "评论成功（部分@用户不存在）";

        return Response.success(msg, vo);
    }

    @Override
    public Response<CommentVO> updateComment(int userId, int commentId, UpdateCommentRequest request) {

        Comment comment = commentMapper.selectById(commentId);
        if (comment == null || comment.getIsDeleted() == 1) {
            return Response.error(404, "评论不存在");
        }

        if (!comment.getUserId().equals(userId)) {
            return Response.error(403, "无权限修改");
        }

        comment.setContent(request.getContent());
        commentMapper.updateById(comment);

        User user = userMapper.selectById(userId);

        CommentVO vo = new CommentVO();
        vo.setId(comment.getId());
        vo.setPostId(comment.getPostId());
        vo.setParentId(comment.getParentId());
        vo.setContent(comment.getContent());
        vo.setUserId(comment.getUserId());

        vo.setUsername(user == null ? null : user.getUsername());
        vo.setNickname(user == null ? null : user.getNickname());
        vo.setAvatar(user == null ? "default.png" : user.getAvatar());

        vo.setLikeCount(comment.getLikeCount());

        vo.setCreateTime(
                comment.getCreateTime() == null ? null :
                        comment.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        vo.setChildren(new ArrayList<>());

        return Response.success("修改成功", vo);
    }

    @Override
    public Response<Void> deleteComment(int userId, int commentId) {

        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            return Response.error(404, "评论不存在");
        }

        if (!comment.getUserId().equals(userId)) {
            return Response.error(403, "无权限删除");
        }

        comment.setIsDeleted(1);
        commentMapper.updateById(comment);
        // 递归删除所有子评论
        deleteCommentRecursively(commentId);

        // 删除该评论的所有子评论（楼中楼）
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, commentId);

        List<Comment> children = commentMapper.selectList(wrapper);
        for (Comment child : children) {
            child.setIsDeleted(1);
            commentMapper.updateById(child);
        }

        Post post = postMapper.selectById(comment.getPostId());

        if (post != null) {
            Post update = new Post();
            update.setId(post.getId());

            Integer count = post.getCommentCount();
            if (count == null || count <= 0) {
                count = 0;
            }

            update.setCommentCount(count - 1);

            postMapper.updateById(update);
        }

        return Response.success("删除成功", null);
    }

    private void deleteCommentRecursively(Integer commentId) {

        // 查出所有直接子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, commentId);

        List<Comment> children = commentMapper.selectList(wrapper);

        for (Comment child : children) {
            // 递归删除子节点
            deleteCommentRecursively(child.getId());

            // 删除当前子节点（逻辑删除）
            child.setIsDeleted(1);
            commentMapper.updateById(child);
        }
    }

    private List<String> extractAtUsernames(String content) {
        List<String> result = new ArrayList<>();

        if (content == null || content.trim().isEmpty()) {
            return result;
        }


        Pattern pattern = Pattern.compile("@([a-zA-Z0-9_\\u4e00-\\u9fa5]+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result.stream().distinct().collect(Collectors.toList());
    }

}