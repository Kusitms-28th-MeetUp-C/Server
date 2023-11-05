package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmap;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CustomRoadmapDetailResponseDto {
    private Long roadmapId;
    private String title;
    private List<CustomRoadmapSpaceDetailResponseDto> roadmapDetailList;

    public static CustomRoadmapDetailResponseDto of(CustomRoadmap roadmap, List<CustomRoadmapSpaceDetailResponseDto> roadmapDetailList) {
        return CustomRoadmapDetailResponseDto.builder()
                .roadmapId(roadmap.getId())
                .title(roadmap.getTitle())
                .roadmapDetailList(roadmapDetailList)
                .build();
    }
}
