package com.kusitms.mainservice.domain.roadmap.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchRoadmapRequestDto {
    private String title;
    private String roadmapType;
}
