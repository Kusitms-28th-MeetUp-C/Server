package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoadmapTitleResponseDto {
    private String title;

    public static RoadmapTitleResponseDto of(String title) {
        return RoadmapTitleResponseDto.builder()
                .title(title)
                .build();
    }
}
