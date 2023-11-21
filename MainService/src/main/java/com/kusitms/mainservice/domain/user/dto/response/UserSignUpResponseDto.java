package com.kusitms.mainservice.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSignUpResponseDto {
    private String name;

    public static UserSignUpResponseDto of(String name){
        return UserSignUpResponseDto.builder()
                .name(name)
                .build();
    }
}
