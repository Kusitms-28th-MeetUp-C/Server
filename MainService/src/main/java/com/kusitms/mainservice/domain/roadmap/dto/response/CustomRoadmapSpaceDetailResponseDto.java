package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapSpace;
import com.kusitms.mainservice.domain.template.dto.response.BaseCustomTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateTitleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CustomRoadmapSpaceDetailResponseDto {
    private Long stepId;
    private int step;
    private String title;
    private List<BaseCustomTemplateResponseDto> templateList;

    public static CustomRoadmapSpaceDetailResponseDto of(CustomRoadmapSpace roadmapSpace, List<BaseCustomTemplateResponseDto> templateList) {
        return CustomRoadmapSpaceDetailResponseDto.builder()
                .stepId(roadmapSpace.getId())
                .step(roadmapSpace.getStep())
                .title(roadmapSpace.getTitle())
                .templateList(templateList)
                .build();
    }
}
