package com.kusitms.mainservice.global.infra.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyPageResponseDto {
    private MyPageUserResponseDto myPageUserResponseDto;
    private MyPageContentResponseDto myPageContentResponseDto;

}
