package com.kusitms.mainservice.domain.user.dto.response;

import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DetailUserResponseDto {
    private Long id;
    private String profile;
    private String name;
    private int templateNum;
    private int roadmapNum;

    public static DetailUserResponseDto of(User user, int templateNum, int roadmapNum){
        return DetailUserResponseDto.builder()
                .id(user.getId())
                .profile(user.getProfile())
                .name(user.getName())
                .templateNum(templateNum)
                .roadmapNum(roadmapNum)
                .build();
    }
}