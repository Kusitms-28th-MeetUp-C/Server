package com.kusitms.mainservice.domain.mypage.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

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
