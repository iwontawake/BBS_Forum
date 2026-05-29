package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankItemVO {
    private Integer rank;
    private Integer userId;
    private String username;
    private String nickname;
    private String avatar;
    private Integer points;
}
