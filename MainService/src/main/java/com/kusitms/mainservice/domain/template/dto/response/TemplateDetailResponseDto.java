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
    private TemplateType templateType;
    private String title;
    private int estimatedTime;
    private List<RoadmapTitleResponseDto> connectedRoadmap;
    private String date;
    private TemplateContentListResponseDto templateContentListResponseDto;
    private String introduction;
    private SearchTemplateResponseDto relatedTemplate;
    private TemplateReviewResponseDto ratingAndReviews;
    private int teamCount;
    private DetailUserResponseDto user;

    public static TemplateDetailResponseDto of(Template template, TemplateContentListResponseDto templateContentListResponseDto, List<RoadmapTitleResponseDto> connectedRoadmap, SearchTemplateResponseDto searchTemplateResponseDto, int teamCount, TemplateReviewResponseDto ratingAndReviews, DetailUserResponseDto user){
        return TemplateDetailResponseDto.builder()
                .templateId(template.getId())
                .templateType(template.getTemplateType())
                .title(template.getTitle())
                .estimatedTime(template.getEstimatedTime())
                .connectedRoadmap(connectedRoadmap)
                .date(template.getDate())
                .templateContentListResponseDto(templateContentListResponseDto)
                .relatedTemplate(searchTemplateResponseDto)
                .ratingAndReviews(ratingAndReviews)
                .teamCount(teamCount)
                .user(user)
                .build();
    }
}
