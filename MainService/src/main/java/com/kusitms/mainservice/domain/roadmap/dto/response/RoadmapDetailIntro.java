package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoadmapDetailIntro {
    private String date;
    private RoadmapDetailBaseIntro simpleInfo;
    private String introduction;

    public static RoadmapDetailIntro of(Roadmap roadmap, RoadmapDetailBaseIntro roadmapDetailBaseIntro){
        return RoadmapDetailIntro.builder()
                .date(roadmap.getDate())
                .simpleInfo(roadmapDetailBaseIntro)
                .introduction(roadmap.getIntroduction())
                .build();
    }
}
