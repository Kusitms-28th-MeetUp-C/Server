package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapSpace;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomRoadmapStepDto {
    private Long stepId;
    private String title;
    public static CustomRoadmapStepDto of(CustomRoadmapSpace customRoadmapSpace){
        return CustomRoadmapStepDto.builder()
                .stepId(customRoadmapSpace.getId())
                .title(customRoadmapSpace.getTitle())
                .build();
    }
}
