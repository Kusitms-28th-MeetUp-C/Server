package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewContentResponseDto {
    private String content;

    public static ReviewContentResponseDto of(String content){
        return ReviewContentResponseDto.builder()
                .content(content)
                .build();
    }
}
