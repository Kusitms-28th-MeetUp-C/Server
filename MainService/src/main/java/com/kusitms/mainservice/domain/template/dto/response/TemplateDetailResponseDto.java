package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class TemplateDetailResponseDto {
    private Long templateId;
    private String templateType;
    private String title;
    private TemplateDetailIntroResponseDto templateIntro;
    private TemplateContentListResponseDto templateContentListResponseDto;
    private TemplateDetailRelateTemplateDto relatedTemplate;
    private String connectedRoadmap;
    private DetailUserResponseDto user;

    public static TemplateDetailResponseDto of(Template template, TemplateDetailIntroResponseDto templateIntro,TemplateContentListResponseDto templateContentListResponseDto, String connectedRoadmap, TemplateDetailRelateTemplateDto relatedTemplate, DetailUserResponseDto user){
        return TemplateDetailResponseDto.builder()
                .templateId(template.getId())
                .templateType(template.getTemplateType().toString())
                .title(template.getTitle())
                .templateIntro(templateIntro)
                .connectedRoadmap(connectedRoadmap)
                .templateContentListResponseDto(templateContentListResponseDto)
                .relatedTemplate(relatedTemplate)
                .user(user)
                .build();
    }
}
