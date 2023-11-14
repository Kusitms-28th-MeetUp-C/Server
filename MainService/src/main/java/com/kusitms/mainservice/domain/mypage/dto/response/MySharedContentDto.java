package com.kusitms.mainservice.domain.mypage.dto.response;

import com.kusitms.mainservice.domain.mypage.domain.SharedType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MySharedContentDto {
    private Long id;
    private SharedType sharedType;
    private String title;
    private String contentType;

    public static MySharedContentDto of(Long id,SharedType sharedType, String title, String contentType){
        return MySharedContentDto.builder()
                .id(id)
                .sharedType(sharedType)
                .title(title)
                .contentType(contentType)
                .build();
    }

}
