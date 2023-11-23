package com.kusitms.mainservice.domain.mypage.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Builder
@Getter
public class MyPageResponseDto {
    private MyPageUserResponseDto user;
    private Page<MySharedContentDto> contentList;

    public static MyPageResponseDto of(MyPageUserResponseDto myPageUserResponseDto, Page<MySharedContentDto> mySharedContentDtoList) {
        return MyPageResponseDto.builder()
                .user(myPageUserResponseDto)
                .contentList(mySharedContentDtoList)
                .build();
    }

}
