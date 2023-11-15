package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchBaseRoadmapResponseDto {

    private Long roadmapId;
    private RoadmapType type;
    private String title;
    private int step;
    private int count;

    public static  SearchBaseRoadmapResponseDto of(Roadmap roadmap, int step ){
        return SearchBaseRoadmapResponseDto.builder()
                .roadmapId(roadmap.getId())
                .type(roadmap.getRoadmapType())
                .title(roadmap.getTitle())
                .step(step)
                .count(roadmap.getCount())
                .build();
    }
}
