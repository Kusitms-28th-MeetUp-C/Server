package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import com.kusitms.mainservice.domain.template.domain.Template;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class TemplateTitleResponseDto {
    private Long templateId;
    private String title;

    public static List<TemplateTitleResponseDto> listOf(List<RoadmapTemplate> roadmapTemplateList){
        List<Template> templateList = roadmapTemplateList.stream()
                .map(RoadmapTemplate::getTemplate).toList();
        return templateList.stream()
                .map(TemplateTitleResponseDto::of)
                .collect(Collectors.toList());
    }

    public static TemplateTitleResponseDto of(Template template) {
        return TemplateTitleResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .build();
    }
}
