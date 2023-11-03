package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDetail;
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

    public static RoadmapDetailResponseDto of(RoadmapDetail roadmapDetail, List<TemplateTitleResponseDto> templateList) {
        return RoadmapDetailResponseDto.builder()
                .stepId(roadmapDetail.getId())
                .step(roadmapDetail.getStep())
                .title(roadmapDetail.getTitle())
                .templateList(templateList)
                .build();
    }
}
