package com.jiang.bbs_forum.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.PostVO;
import com.jiang.bbs_forum.entity.Favorite;
import com.jiang.bbs_forum.entity.Post;
import com.jiang.bbs_forum.mapper.FavoriteMapper;
import com.jiang.bbs_forum.mapper.PostMapper;
import com.jiang.bbs_forum.service.user.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public Response<Void> favorite(int userId, int postId) {

        // 查询是否已收藏（利用唯一索引防重复）
        Favorite exist = favoriteMapper.selectOne(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .eq(Favorite::getPostId, postId)
        );

        // 已收藏则直接返回成功（幂等处理）
        if (exist != null) {
            return Response.success(null);
        }

        // 插入收藏记录
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setPostId(postId);

        favoriteMapper.insert(favorite);

        return Response.success(null);
    }

    @Override
    public Response<Void> unfavorite(int userId, int postId) {

        // 删除收藏记录
        favoriteMapper.delete(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .eq(Favorite::getPostId, postId)
        );

        return Response.success(null);
    }

    @Override
    public Response<PageResponse<PostVO>> listFavorites(int userId, int page, int size) {

        // 计算分页偏移量
        int offset = (page - 1) * size;

        // 查询收藏的帖子ID列表
        List<Integer> postIds = favoriteMapper.selectObjs(
                new LambdaQueryWrapper<Favorite>()
                        .select(Favorite::getPostId)
                        .eq(Favorite::getUserId, userId)
                        .orderByDesc(Favorite::getCreateTime)
                        .last("limit " + offset + "," + size)
        ).stream().map(o -> (Integer) o).toList();

        if (postIds.isEmpty()) {
            return Response.success(new PageResponse<>(0L, List.of(), page, size, 0));
        }

        // 查询帖子详情
        List<Post> posts = postMapper.selectBatchIds(postIds);

        // 转换为VO
        List<PostVO> list = posts.stream().map(post -> {
            PostVO vo = new PostVO();
            vo.setId(post.getId());
            vo.setTitle(post.getTitle());
            vo.setContent(post.getContent());
            vo.setLikeCount(post.getLikeCount());
            vo.setCommentCount(post.getCommentCount());

            vo.setFavorited(true);
            vo.setLiked(false);

            return vo;
        }).toList();

        // 统计总数
        Long total = favoriteMapper.selectCount(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
        );

        int pages = (int) Math.ceil((double) total / size);

        // 封装分页结果
        PageResponse<PostVO> result = new PageResponse<>(
                total,
                list,
                page,
                size,
                pages
        );

        return Response.success(result);
    }
}