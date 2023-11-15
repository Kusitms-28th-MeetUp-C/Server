package com.kusitms.mainservice.domain.mypage.dto.resquest;

import com.kusitms.mainservice.domain.mypage.domain.SharedType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotMyPageRequestDto {
    private Long userId;
    private String sharedType;
}
