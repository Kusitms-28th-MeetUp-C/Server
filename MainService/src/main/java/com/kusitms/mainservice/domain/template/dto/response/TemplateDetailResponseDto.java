package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class TemplateDetailResponseDto {
    private Long templateId;
    private String templateType;
    private String title;
    private TemplateDetailIntroResponseDto templateIntro;
    private String templateContent;
    private List<TemplateDetailBaseRelateDto> relatedTemplate;
    private TemplateDetailConnectRoadmapDto roadmapIdAndConnectRoadmap;
    private DetailUserResponseDto user;

    public static TemplateDetailResponseDto of(Template template, TemplateDetailIntroResponseDto templateIntro, String templateContent, TemplateDetailConnectRoadmapDto roadmapIdAndConnectRoadmap, List<TemplateDetailBaseRelateDto> relatedTemplate, DetailUserResponseDto user) {
        return TemplateDetailResponseDto.builder()
                .templateId(template.getId())
                .templateType(template.getTemplateType().toString())
                .title(template.getTitle())
                .templateIntro(templateIntro)
                .roadmapIdAndConnectRoadmap(roadmapIdAndConnectRoadmap)
                .templateContent(templateContent)
                .relatedTemplate(relatedTemplate)
                .user(user)
                .build();
    }
}
