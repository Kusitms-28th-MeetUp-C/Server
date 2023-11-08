package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamResponseDto;
import com.kusitms.mainservice.domain.template.domain.CustomTemplate;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomTemplateDetailResponseDto {
    private Long templateId;
    private String templateName;
    private TemplateType templateType;
    private String content;
    private CustomRoadmapDetailResponseDto roadmapInfo;
    private TeamResponseDto teamInfo;

    public static CustomTemplateDetailResponseDto of(CustomTemplate template, String content, CustomRoadmapDetailResponseDto roadmapInfo,
                                                     TeamResponseDto teamInfo) {
        return CustomTemplateDetailResponseDto.builder()
                .templateId(template.getId())
                .templateName(template.getTitle())
                .templateType(template.getTemplateType())
                .content(content)
                .roadmapInfo(roadmapInfo)
                .teamInfo(teamInfo)
                .build();
    }
}
