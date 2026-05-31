package com.jiang.bbs_forum.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.LikeBatchRequest;
import com.jiang.bbs_forum.dto.response.LikeBatchVO;
import com.jiang.bbs_forum.entity.Comment;
import com.jiang.bbs_forum.entity.Like;
import com.jiang.bbs_forum.entity.Post;
import com.jiang.bbs_forum.mapper.CommentMapper;
import com.jiang.bbs_forum.mapper.LikeMapper;
import com.jiang.bbs_forum.mapper.PostMapper;
import com.jiang.bbs_forum.service.user.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 用户点赞
     *
     * @param userId 用户ID
     * @param targetType 点赞目标类型（1-帖子，2-评论）
     * @param targetId 点赞目标ID
     * @return 操作结果
     */
    @Override
    public Response<Void> like(int userId, int targetType, int targetId) {

        if (targetType == 1 && postMapper.selectById(targetId) == null) {
            return Response.error(404, "帖子不存在");
        }

        if (targetType == 2 && commentMapper.selectById(targetId) == null) {
            return Response.error(404, "评论不存在");
        }

        // 查询当前用户是否已经点赞过该目标
        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getUserId, userId)
                .eq(Like::getTargetType, targetType)
                .eq(Like::getTargetId, targetId);

        // 已点赞则直接返回
        if (likeMapper.selectCount(wrapper) > 0) {
            return Response.success("已点赞", null);
        }

        // 创建点赞记录
        Like like = new Like();
        like.setUserId(userId);
        like.setTargetType(targetType);
        like.setTargetId(targetId);

        // 保存点赞记录
        likeMapper.insert(like);

        // 更新帖子或评论的点赞数量
        updateLikeCount(targetType, targetId, 1);

        return Response.success("点赞成功", null);
    }

    /**
     * 取消点赞
     *
     * @param userId 用户ID
     * @param targetType 点赞目标类型（1-帖子，2-评论）
     * @param targetId 点赞目标ID
     * @return 操作结果
     */
    @Override
    public Response<Void> unlike(int userId, int targetType, int targetId) {

        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getUserId, userId)
                .eq(Like::getTargetType, targetType)
                .eq(Like::getTargetId, targetId);

        // 删除点赞记录
        int deleted = likeMapper.delete(wrapper);

        // 删除成功后同步减少点赞数
        if (deleted > 0) {
            updateLikeCount(targetType, targetId, -1);
        }

        return Response.success("取消成功", null);
    }

    /**

     * 更新点赞数量
     *
     * @param targetType 点赞目标类型（1-帖子，2-评论）
     * @param targetId 点赞目标ID
     * @param delta 增量（1表示增加，-1表示减少）
     */
    private void updateLikeCount(int targetType, int targetId, int delta) {

        // 更新帖子点赞数
        if (targetType == 1) {

            Post post = postMapper.selectById(targetId);

            if (post != null) {

                Integer count = post.getLikeCount();

                if (count == null) {
                    count = 0;
                }

                post.setLikeCount(
                        Math.max(0, count + delta)
                );

                postMapper.updateById(post);
            }

        }

        // 更新评论点赞数
        if (targetType == 2) {

            Comment comment = commentMapper.selectById(targetId);

            if (comment != null) {

                Integer count = comment.getLikeCount();

                if (count == null) {
                    count = 0;
                }

                comment.setLikeCount(
                        Math.max(0, count + delta)
                );

                commentMapper.updateById(comment);
            }

        }
    }


    /**
     * 查询当前用户是否已点赞
     *
     * @param userId 用户ID
     * @param targetType 点赞目标类型（1-帖子，2-评论）
     * @param targetId 点赞目标ID
     * @return true-已点赞，false-未点赞
     */
    @Override
    public Response<Boolean> isLiked(int userId, int targetType, int targetId) {

        System.out.println("isLiked check: userId=" + userId);
        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getUserId, userId)
                .eq(Like::getTargetType, targetType)
                .eq(Like::getTargetId, targetId);

        Long count = likeMapper.selectCount(wrapper);

        return Response.success(count > 0);
    }

    @Override
    public Response<LikeBatchVO> batchStatus(int userId, LikeBatchRequest request) {

        LikeBatchVO vo = new LikeBatchVO();
        Map<Integer, Boolean> map = new HashMap<>();

        // 1. 查询帖子点赞状态
        if (request.getPostId() != null) {

            LambdaQueryWrapper<Like> postWrapper = new LambdaQueryWrapper<>();
            postWrapper.eq(Like::getUserId, userId)
                    .eq(Like::getTargetType, 1)
                    .eq(Like::getTargetId, request.getPostId());

            vo.setPostLiked(likeMapper.selectCount(postWrapper) > 0);
        }

        // 2. 批量查询评论点赞状态（优化关键）
        if (request.getCommentIds() != null && !request.getCommentIds().isEmpty()) {

            LambdaQueryWrapper<Like> commentWrapper = new LambdaQueryWrapper<>();
            commentWrapper.eq(Like::getUserId, userId)
                    .eq(Like::getTargetType, 2)
                    .in(Like::getTargetId, request.getCommentIds());

            List<Like> likes = likeMapper.selectList(commentWrapper);

            Set<Integer> likedIds = likes.stream()
                    .map(Like::getTargetId)
                    .collect(Collectors.toSet());

            for (Integer cid : request.getCommentIds()) {
                map.put(cid, likedIds.contains(cid));
            }
        }

        vo.setCommentLikeMap(map);

        return Response.success(vo);
    }

}
