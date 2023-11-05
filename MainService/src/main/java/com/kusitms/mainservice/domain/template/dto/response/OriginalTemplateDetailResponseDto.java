package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.RelatedRoadmapResponseDto;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OriginalTemplateDetailResponseDto {
    private String title;
    private TemplateType templateType;
    private String contents;
    private List<RelatedTemplateResponseDto> relatedTemplateList;
    private List<RelatedRoadmapResponseDto> relatedRoadmapList;

    public static OriginalTemplateDetailResponseDto of(Template template, String contents, List<RelatedTemplateResponseDto> relatedTemplateList, List<RelatedRoadmapResponseDto> relatedRoadmapList){
        return OriginalTemplateDetailResponseDto.builder()
                .title(template.getTitle())
                .templateType(template.getTemplateType())
                .contents(contents)
                .relatedTemplateList(relatedTemplateList)
                .relatedRoadmapList(relatedRoadmapList)
                .build();
    }
}
