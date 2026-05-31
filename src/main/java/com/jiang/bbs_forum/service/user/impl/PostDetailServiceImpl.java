package com.jiang.bbs_forum.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiang.bbs_forum.common.PageResponse;
import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.response.CommentVO;
import com.jiang.bbs_forum.dto.response.PostDetailVO;
import com.jiang.bbs_forum.dto.response.PostVO;
import com.jiang.bbs_forum.entity.Board;
import com.jiang.bbs_forum.entity.Like;
import com.jiang.bbs_forum.entity.Post;
import com.jiang.bbs_forum.entity.User;
import com.jiang.bbs_forum.mapper.BoardMapper;
import com.jiang.bbs_forum.mapper.LikeMapper;
import com.jiang.bbs_forum.mapper.PostMapper;
import com.jiang.bbs_forum.mapper.UserMapper;
import com.jiang.bbs_forum.service.user.CommentService;
import com.jiang.bbs_forum.service.user.PostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

@Service
public class PostDetailServiceImpl implements PostDetailService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public Response<PostDetailVO> getPostDetail(Integer userId, int postId, int page, int size) {

        // 查询帖子
        Post post = postMapper.selectById(postId);
        if (post == null || post.getIsDeleted() == 1) {
            return Response.error(404, "帖子不存在");
        }

        // 构建 PostVO（基础字段）
        PostVO postVO = new PostVO();
        postVO.setId(post.getId());
        postVO.setTitle(post.getTitle());
        postVO.setContent(post.getContent());
        postVO.setLikeCount(post.getLikeCount());
        postVO.setCommentCount(post.getCommentCount());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        postVO.setCreateTime(
                post.getCreateTime() == null ? null :
                        post.getCreateTime().format(formatter)
        );

        postVO.setUpdateTime(
                post.getUpdateTime() == null ? null :
                        post.getUpdateTime().format(formatter)
        );

        // 补充用户信息
        if (post.getUserId() != null) {
            User user = userMapper.selectById(post.getUserId());
            postVO.setUserId(post.getUserId());
            postVO.setUsername(user == null ? null : user.getUsername());
            postVO.setNickname(user == null ? null : user.getNickname());
            postVO.setAvatar(user == null ? null : user.getAvatar());
        }

        // 补充板块信息
        if (post.getBoardId() != null) {
            Board board = boardMapper.selectById(post.getBoardId());
            postVO.setBoardId(post.getBoardId());
            postVO.setBoardName(board == null ? null : board.getName());
        }

        // 查询点赞状态（用户态）
        if (userId != null) {
            boolean liked = likeMapper.selectCount(
                    new LambdaQueryWrapper<Like>()
                            .eq(Like::getUserId, userId)
                            .eq(Like::getTargetType, 1)
                            .eq(Like::getTargetId, postId)
            ) > 0;

            postVO.setLiked(liked);
        } else {
            postVO.setLiked(false);
        }

        // 获取评论
        Response<PageResponse<CommentVO>> commentResp =
                commentService.listComments(userId, postId, page, size);

        // 组装返回
        PostDetailVO detailVO = new PostDetailVO();
        detailVO.setPost(postVO);
        detailVO.setComments(commentResp.getData());

        return Response.success(detailVO);
    }
}