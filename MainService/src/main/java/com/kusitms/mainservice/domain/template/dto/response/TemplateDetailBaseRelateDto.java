package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateDetailBaseRelateDto {
    private Long templateId;
    private String title;
    private int teamCount;
    private int estimatedTime;
    private double rating;
    private RoadmapTitleResponseDto connectedRoadmap;
    public static TemplateDetailBaseRelateDto of(Template template, int teamCount, double rating){
        return TemplateDetailBaseRelateDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .estimatedTime(template.getEstimatedTime())
                .teamCount(teamCount)
                .rating(rating)
                .build();

    }
}
