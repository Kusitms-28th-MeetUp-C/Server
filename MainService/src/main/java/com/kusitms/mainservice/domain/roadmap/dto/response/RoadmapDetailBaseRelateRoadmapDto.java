package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class RoadmapDetailBaseRelateRoadmapDto {
    private Long roadmapId;
    private String title;
    private int step;
    private int count;

    public static RoadmapDetailBaseRelateRoadmapDto of(Roadmap roadmap, int count) {
        return RoadmapDetailBaseRelateRoadmapDto.builder()
                .roadmapId(roadmap.getId())
                .title(roadmap.getTitle())
                .step(roadmap.getRoadmapSpaceList().size())
                .count(count)
                .build();
    }
}
