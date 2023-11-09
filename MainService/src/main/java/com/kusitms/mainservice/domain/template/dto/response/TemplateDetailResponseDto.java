package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class TemplateDetailResponseDto {
    private Long templateId;
    private int estimatedTime;
    private List<RoadmapTitleResponseDto> connectedRoadmap;
    private String createdAt;
    private String content;
    private String introduction;
    private SearchTemplateResponseDto relatedTemplate;
    private RatingResponseDto averageRating;
    private int teamCount;
    private List<ReviewContentResponseDto> reviews;
    private TemplateDetailUserResponseDto user;

    public static TemplateDetailResponseDto of(Optional<Template> template, Optional<TemplateContent> templateContent, List<RoadmapTitleResponseDto> connectedRoadmap,SearchTemplateResponseDto searchTemplateResponseDto, RatingResponseDto averageRating, int teamCount, List<ReviewContentResponseDto> reviews, TemplateDetailUserResponseDto user, String createdAt){
        return TemplateDetailResponseDto.builder()
                .templateId(template.get().getId())
                .estimatedTime(template.get().getEstimatedTime())
                .connectedRoadmap(connectedRoadmap)
                .createdAt(createdAt)
                .content(templateContent.get().getContent())
                .introduction(templateContent.get().getIntroduction())
                .relatedTemplate(searchTemplateResponseDto)
                .averageRating(averageRating)
                .teamCount(teamCount)
                .reviews(reviews)
                .user(user)
                .build();
    }
}
