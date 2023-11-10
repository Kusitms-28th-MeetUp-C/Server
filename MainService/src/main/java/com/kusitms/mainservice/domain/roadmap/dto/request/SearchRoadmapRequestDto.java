package com.kusitms.mainservice.domain.roadmap.dto.request;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import lombok.Getter;

@Getter
public class SearchRoadmapRequestDto {
    private String title;
    private RoadmapType roadmapType;
}
