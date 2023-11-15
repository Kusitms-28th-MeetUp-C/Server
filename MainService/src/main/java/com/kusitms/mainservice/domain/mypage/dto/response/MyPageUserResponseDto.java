package com.kusitms.mainservice.domain.mypage.dto.response;

import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.domain.UserType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyPageUserResponseDto {
    private Long userId;
    private String profile;
    private UserType userType;
    private String name;
    private String email;
    private int templateNum;
    private int roadmapNum;
    private int point;
    public static MyPageUserResponseDto of(User user,int templateNum, int roadmapNum, int point){
        return MyPageUserResponseDto.builder()
                .userId(user.getId())
                .profile(user.getProfile())
                .userType(user.getUserType())
                .name(user.getName())
                .email(user.getEmail())
                .templateNum(templateNum)
                .roadmapNum(roadmapNum)
                .point(point)
                .build();
    }
}
