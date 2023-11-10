package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class SearchBaseTemplateResponseDto {
    private Long templateId;
    private String title;
    private TemplateType templateType;
    private int count;
    private int estimatedTime;
    private List<RoadmapTitleResponseDto> connectedRoadmap;

    public static SearchBaseTemplateResponseDto of(Template template, int count,List<RoadmapTitleResponseDto> connectedRoadmap){
        return SearchBaseTemplateResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .templateType(template.getTemplateType())
                .count(count)
                .estimatedTime(template.getEstimatedTime())
                .connectedRoadmap(connectedRoadmap)
                .build();
    }
}
