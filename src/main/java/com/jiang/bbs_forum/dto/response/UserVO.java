package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private Integer id;
    private String username;
    private String email;
    private String nickname;
    private String avatar;
    private String role;
    private Integer status;
    private Integer points;
    private String lastLoginTime;
    private Integer loginCount;
    private String createTime;
    private ProfileVO profile;
}
