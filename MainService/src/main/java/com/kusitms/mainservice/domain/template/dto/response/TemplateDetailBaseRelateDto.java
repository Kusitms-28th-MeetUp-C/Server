package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
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
    private String connectedRoadmap;

    public static TemplateDetailBaseRelateDto of(Template template, int teamCount, double rating, String connectedRoadmap) {
        return TemplateDetailBaseRelateDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .estimatedTime(template.getEstimatedTime())
                .teamCount(teamCount)
                .rating(rating)
                .connectedRoadmap(connectedRoadmap)
                .build();

    }
}
