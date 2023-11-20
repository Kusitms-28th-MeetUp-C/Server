package com.kusitms.mainservice.domain.user.dto.response;

import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DetailUserResponseDto {
    private Long id;
    private String profile;
    private String userType;
    private String name;
    private String email;
    private int templateNum;
    private int roadmapNum;
    private String sessionId;

    public static DetailUserResponseDto of(User user, int templateNum, int roadmapNum){
        return DetailUserResponseDto.builder()
                .id(user.getId())
                .profile(user.getProfile())
                .userType(user.getUserType().getStringUserType())
                .name(user.getName())
                .email(user.getEmail())
                .templateNum(templateNum)
                .roadmapNum(roadmapNum)
                .sessionId(user.getSessionId())
                .build();
    }
}
