package com.kusitms.mainservice.domain.user.dto.response;

import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.global.config.auth.TokenInfo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAuthResponseDto {
    private Long id;
    private String email;
    private String name;
    private String picture;
    private String accessToken;
    private String refreshToken;

    public static UserAuthResponseDto of(User user, TokenInfo token) {
        return UserAuthResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .picture(user.getProfile())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
