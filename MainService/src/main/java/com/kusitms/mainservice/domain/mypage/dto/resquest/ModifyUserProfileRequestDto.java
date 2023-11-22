package com.kusitms.mainservice.domain.mypage.dto.resquest;

import com.kusitms.mainservice.domain.user.domain.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyUserProfileRequestDto {
    private String profile;
    private String name;
    private String userType;

}
