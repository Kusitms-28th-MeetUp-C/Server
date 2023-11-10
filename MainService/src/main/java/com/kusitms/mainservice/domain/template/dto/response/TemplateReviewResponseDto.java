package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TemplateReviewResponseDto {
    private double ratingAverage;
    private List<ReviewContentResponseDto> reviews;
    public static TemplateReviewResponseDto of(double ratingAverage, List<ReviewContentResponseDto> reviews){
        return TemplateReviewResponseDto.builder()
                .ratingAverage(ratingAverage)
                .reviews(reviews)
                .build();
    }
}
