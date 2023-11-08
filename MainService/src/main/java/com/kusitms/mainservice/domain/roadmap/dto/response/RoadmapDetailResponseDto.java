package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.template.dto.response.TemplateTitleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class RoadmapDetailResponseDto {
    private Long stepId;
    private int step;
    private String title;
    private List<TemplateTitleResponseDto> templateList;

    public static List<RoadmapDetailResponseDto> listOf(Roadmap roadmap){
        return roadmap.getRoadmapSpaceList().stream()
                .map(roadmapSpace ->
                        RoadmapDetailResponseDto.of(
                                roadmapSpace,
                                TemplateTitleResponseDto.listOf(roadmapSpace.getRoadmapTemplateList())))
                .collect(Collectors.toList());
    }

    public static RoadmapDetailResponseDto of(RoadmapSpace roadmapSpace, List<TemplateTitleResponseDto> templateList) {
        return RoadmapDetailResponseDto.builder()
                .stepId(roadmapSpace.getId())
                .step(roadmapSpace.getStep())
                .title(roadmapSpace.getTitle())
                .templateList(templateList)
                .build();
    }
}
