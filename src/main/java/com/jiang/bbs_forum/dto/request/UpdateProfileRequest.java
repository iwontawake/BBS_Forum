package com.jiang.bbs_forum.dto.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String nickname;
    private String phone;
    private String workNature;
    private String workLocation;
    private String signature;
}
