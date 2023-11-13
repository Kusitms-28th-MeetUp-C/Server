package com.kusitms.mainservice.global.infra.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class MyPageResponseDto {
    private MyPageUserResponseDto myPageUserResponseDto;
    private Page<MySharedContentDto> mySharedContentDtoList;

    public static MyPageResponseDto of(MyPageUserResponseDto myPageUserResponseDto,Page<MySharedContentDto> mySharedContentDtoList){
        return MyPageResponseDto.builder()
                .myPageUserResponseDto(myPageUserResponseDto)
                .mySharedContentDtoList(mySharedContentDtoList)
                .build();
    }

}
