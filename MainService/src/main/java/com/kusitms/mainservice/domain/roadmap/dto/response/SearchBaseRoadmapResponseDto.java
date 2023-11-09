package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchBaseRoadmapResponseDto {

    private Long roadmapId;
    private RoadmapType roadmapType;
    private String title;
    private int step;
    private int count;

}
