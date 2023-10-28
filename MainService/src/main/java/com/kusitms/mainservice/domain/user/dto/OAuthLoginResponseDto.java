package com.kusitms.mainservice.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginResponseDto {
    private Long id;
    private String email;
    private String picture;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenRemainTime;
    private Long refreshTokenRemainTime;
}
