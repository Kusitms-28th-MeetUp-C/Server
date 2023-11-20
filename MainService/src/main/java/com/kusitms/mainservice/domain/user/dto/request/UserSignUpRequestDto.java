package com.kusitms.mainservice.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserSignUpRequestDto {
    private String userName;
    private String userType;
    private String teamName;
}
