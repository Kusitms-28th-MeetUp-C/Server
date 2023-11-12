package com.kusitms.mainservice.domain.roadmap.dto.request;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchRoadmapRequestDto {
    private String title;
    private String roadmapType;
}
