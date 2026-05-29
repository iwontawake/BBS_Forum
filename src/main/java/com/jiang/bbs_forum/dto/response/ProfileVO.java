package com.jiang.bbs_forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileVO {
    private String nickname;
    private String avatar;
    private String phone;
    private String workNature;
    private String workLocation;
    private String signature;
}
