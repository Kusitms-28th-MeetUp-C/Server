package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateDetailIntroBaseResponseDto {
    private double rating;
    private int estimateTime;
    private int teamCount;
    private int reviewCount;

    public static TemplateDetailIntroBaseResponseDto of(double rating, int estimateTime, int teamCount, int reviewCount){
        return TemplateDetailIntroBaseResponseDto.builder()
                .rating(rating)
                .estimateTime(estimateTime)
                .teamCount(teamCount)
                .reviewCount(reviewCount)
                .build();
    }
}
