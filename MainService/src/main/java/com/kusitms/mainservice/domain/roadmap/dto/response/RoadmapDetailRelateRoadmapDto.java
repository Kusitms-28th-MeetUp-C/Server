package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class RoadmapDetailRelateRoadmapDto {
    private List<RoadmapDetailBaseRelateRoadmapDto> roadmapDetailBaseRelateRoadmapDtoList;
    public static RoadmapDetailRelateRoadmapDto of(List<RoadmapDetailBaseRelateRoadmapDto> roadmapDetailBaseRelateRoadmapDtoList){
        return RoadmapDetailRelateRoadmapDto.builder()
                .roadmapDetailBaseRelateRoadmapDtoList(roadmapDetailBaseRelateRoadmapDtoList)
                .build();
    }
}
