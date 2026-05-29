package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostVO {
    private Integer id;
    private String title;
    private String content;
    private Integer boardId;
    private String boardName;
    private Integer userId;
    private String username;
    private String nickname;
    private String avatar;
    private Integer isTop;
    private Integer isEssence;
    private Integer isDemand;
    private Integer rewardPoints;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private String createTime;
    private String updateTime;
}
