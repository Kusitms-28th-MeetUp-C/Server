package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BaseRoadmapResponseDto {
    private Long roadmapId;
    private String title;
    private List<RoadmapDetailResponseDto> roadmapList;

    public static BaseRoadmapResponseDto of(Roadmap roadmap, List<RoadmapDetailResponseDto> roadmapDetailList) {
        return BaseRoadmapResponseDto.builder()
                .roadmapId(roadmap.getId())
                .title(roadmap.getTitle())
                .roadmapList(roadmapDetailList)
                .build();
    }
}
