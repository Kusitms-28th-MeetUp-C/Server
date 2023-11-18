package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class CustomRoadmapDetailResponseDto {
    private Long roadmapId;
    private String title;
    private RoadmapType roadmapType;
    private LocalDate startTime;
    private LocalDate endTime;
    private List<CustomRoadmapSpaceDetailResponseDto> roadmapList;

    public static CustomRoadmapDetailResponseDto of(CustomRoadmap roadmap, List<CustomRoadmapSpaceDetailResponseDto> roadmapDetailList) {
        return CustomRoadmapDetailResponseDto.builder()
                .roadmapId(roadmap.getId())
                .title(roadmap.getTitle())
                .roadmapType(roadmap.getRoadmapType())
                .startTime(roadmap.getStartDate())
                .endTime(roadmap.getEndDate())
                .roadmapList(roadmapDetailList)
                .build();
    }
}
