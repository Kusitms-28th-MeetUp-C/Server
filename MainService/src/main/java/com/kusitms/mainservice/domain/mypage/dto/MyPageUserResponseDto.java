package com.kusitms.mainservice.domain.mypage.dto;

import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyPageUserResponseDto {
    private Long userId;
    private String name;
    private String email;
    private int templateNum;
    private int roadmapNum;
    private int point;
    public static MyPageUserResponseDto of(User user,int templateNum, int roadmapNum, int point){
        return MyPageUserResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .templateNum(templateNum)
                .roadmapNum(roadmapNum)
                .point(point)
                .build();
    }
}
