package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TemplateDetailUserResponseDto {
    private Long id;
    private String profile;
    private String name;

    public static TemplateDetailUserResponseDto of(User user){
        return TemplateDetailUserResponseDto.builder()
                .id(user.getId())
                .profile(user.getProfile())
                .name(user.getName())
                .build();
    }
}
