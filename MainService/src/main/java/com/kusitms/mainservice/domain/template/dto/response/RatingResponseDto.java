package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RatingResponseDto {
    private double ratingAverage;

    public static RatingResponseDto of(double ratingAverage){
        return RatingResponseDto.builder()
                .ratingAverage(ratingAverage)
                .build();
    }
}
