package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.template.dto.response.TemplateTitleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RoadmapDetailResponseDto {
    private Long stepId;
    private int step;
    private String title;
    private List<TemplateTitleResponseDto> templateList;

    public static RoadmapDetailResponseDto of(RoadmapSpace roadmapSpace, List<TemplateTitleResponseDto> templateList) {
        return RoadmapDetailResponseDto.builder()
                .stepId(roadmapSpace.getId())
                .step(roadmapSpace.getStep())
                .title(roadmapSpace.getTitle())
                .templateList(templateList)
                .build();
    }
}
