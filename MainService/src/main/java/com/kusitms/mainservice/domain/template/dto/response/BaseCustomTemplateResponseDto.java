package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapTemplate;
import com.kusitms.mainservice.domain.template.domain.CustomTemplate;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class BaseCustomTemplateResponseDto {
    private Long templateId;
    private String title;
    private TemplateType templateType;

    public static List<BaseCustomTemplateResponseDto> listOf(CustomRoadmapSpace customRoadmapSpace) {
        List<CustomRoadmapTemplate> customRoadmapTemplateList = customRoadmapSpace.getCustomRoadmapTemplateList();
        return customRoadmapTemplateList.stream()
                .map(customRoadmapTemplate -> BaseCustomTemplateResponseDto.of(customRoadmapTemplate.getCustomTemplate()))
                .collect(Collectors.toList());
    }

    public static BaseCustomTemplateResponseDto of(CustomTemplate template) {
        return BaseCustomTemplateResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .templateType(template.getTemplateType())
                .build();
    }
}
