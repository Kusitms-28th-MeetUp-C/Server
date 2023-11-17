package com.kusitms.mainservice.domain.roadmap.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoadmapSharingRequestDto {
    private String title;
    private String introduction;
    private List<StepDto> steps;
    private String roadmapType;


}

