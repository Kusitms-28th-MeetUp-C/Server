package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchBaseTemplateResponseDto {
    private Long templateId;
    private String title;
    private TemplateType type;
    private int count;
    private int estimatedTime;
    private String connectedRoadmap;

    public static SearchBaseTemplateResponseDto of(Template template, int count, String connectedRoadmap) {
        return SearchBaseTemplateResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .type(template.getTemplateType())
                .count(count)
                .estimatedTime(template.getEstimatedTime())
                .connectedRoadmap(connectedRoadmap)
                .build();
    }
}
