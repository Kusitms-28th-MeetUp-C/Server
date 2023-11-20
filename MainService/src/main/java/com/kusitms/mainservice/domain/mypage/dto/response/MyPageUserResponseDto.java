package com.kusitms.mainservice.domain.mypage.dto.response;

import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyPageUserResponseDto {
    private Long userId;
    private String profile;
    private String userType;
    private String name;
    private String email;
    private int templateNum;
    private int roadmapNum;
    private int point;
    private String sessionId;
    public static MyPageUserResponseDto of(User user,int templateNum, int roadmapNum, int point){
        return MyPageUserResponseDto.builder()
                .userId(user.getId())
                .profile(user.getProfile())
                .userType(user.getUserType().getStringUserType())
                .name(user.getName())
                .email(user.getEmail())
                .templateNum(templateNum)
                .roadmapNum(roadmapNum)
                .point(point)
                .sessionId(user.getSessionId())
                .build();
    }
}
