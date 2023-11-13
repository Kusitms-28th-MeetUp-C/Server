package com.kusitms.mainservice.global.infra.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MySharedContentDto {
    private SharedType sharedType;
    private String title;
    private String contentType;

    public static MySharedContentDto of(SharedType sharedType, String title, String contentType){
        return MySharedContentDto.builder()
                .title(title)
                .contentType(contentType)
                .build();
    }

}
