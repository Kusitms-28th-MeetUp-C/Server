package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapTitleResponseDto {
    private String title;

    public static RoadmapTitleResponseDto of(String title) {
        return RoadmapTitleResponseDto.builder()
                .title(title)
                .build();
    }
}
