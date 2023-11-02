package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDetail;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoadmapDetailResponseDto {
    private Long stepId;
    private int step;
    private String title;

    public static RoadmapDetailResponseDto of(RoadmapDetail roadmapDetail){
        return RoadmapDetailResponseDto.builder()
                .stepId(roadmapDetail.getId())
                .step(roadmapDetail.getStep())
                .title(roadmapDetail.getTitle())
                .build();
    }
}
