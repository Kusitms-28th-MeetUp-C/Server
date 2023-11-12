package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoadmapDetailIntro {
    private RoadmapDetailBaseIntro roadmapDetailBaseIntro;
    private String introduction;

    public static RoadmapDetailIntro of(RoadmapDetailBaseIntro roadmapDetailBaseIntro, String introduction){
        return RoadmapDetailIntro.builder()
                .roadmapDetailBaseIntro(roadmapDetailBaseIntro)
                .introduction(introduction)
                .build();
    }
}
