package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
    private Integer id;
    private Integer postId;
    private String postTitle;
    private Integer parentId;
    private String content;
    private Integer userId;
    private String username;
    private String nickname;
    private String avatar;
    private Integer likeCount;
    private String createTime;
    private List<CommentVO> children;
}
