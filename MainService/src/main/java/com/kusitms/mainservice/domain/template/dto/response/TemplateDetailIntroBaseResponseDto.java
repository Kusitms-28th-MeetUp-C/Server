package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateDetailIntroBaseResponseDto {
    private double rating;
    private int estimatedTime;
    private int teamCount;
    private int reviewCount;

    public static TemplateDetailIntroBaseResponseDto of(double rating, int estimatedTime, int teamCount, int reviewCount){
        return TemplateDetailIntroBaseResponseDto.builder()
                .rating(rating)
                .estimatedTime(estimatedTime)
                .teamCount(teamCount)
                .reviewCount(reviewCount)
                .build();
    }
}
