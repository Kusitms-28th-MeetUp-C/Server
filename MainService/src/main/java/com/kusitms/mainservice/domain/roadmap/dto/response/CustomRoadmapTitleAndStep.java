package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomRoadmapTitleAndStep {
    private String title;
    private List<CustomRoadmapStepDto> stepList;

    public static CustomRoadmapTitleAndStep of( List<CustomRoadmapStepDto> customRoadmapStepDtoList, String customRoadmapTitle){
        return CustomRoadmapTitleAndStep.builder()
                .title(customRoadmapTitle)
                .stepList(customRoadmapStepDtoList)
                .build();
    }
}
