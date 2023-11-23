package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoadmapDetailBaseIntro {
    private int step;
    private int teamCount;

    public static RoadmapDetailBaseIntro of(Roadmap roadmap, int teamCount) {
        return RoadmapDetailBaseIntro.builder()
                .step(roadmap.getRoadmapSpaceList().size())
                .teamCount(teamCount)
                .build();
    }
}
