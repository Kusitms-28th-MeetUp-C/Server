package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TemplateReviewResponseDto {
    private double ratingAverage;
    private int reviewCount;
    public static TemplateReviewResponseDto of(double ratingAverage, int reviewCount){
        return TemplateReviewResponseDto.builder()
                .ratingAverage(ratingAverage)
                .reviewCount(reviewCount)
                .build();
    }
}
