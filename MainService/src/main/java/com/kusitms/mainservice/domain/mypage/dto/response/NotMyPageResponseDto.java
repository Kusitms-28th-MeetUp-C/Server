package com.kusitms.mainservice.domain.mypage.dto.response;

import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotMyPageResponseDto {
    private DetailUserResponseDto user;

}
