package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.template.domain.Rating;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.UserAuthResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TemplateDetailResponseDto {
    private Long templateId;
    private int estimatedTime;
    private List<RoadmapTitleResponseDto> connectedRoadmap;
    private int upload;
    private String content;
    private String introduction;
    private List<SearchBaseTemplateResponseDto> relatedTemplate;
    private RatingResponseDto averageRating;
    private int teamCount;
    private List<ReviewContentResponseDto> reviews;
    private TemplateDetailUserResponseDto user;

    public static TemplateDetailResponseDto of(Template template, TemplateContent templateContent,List<RoadmapTitleResponseDto> connectedRoadmap,List<SearchBaseTemplateResponseDto> relatedTemplate, RatingResponseDto averageRating,int teamCount, List<ReviewContentResponseDto> reviews, TemplateDetailUserResponseDto user){
        return TemplateDetailResponseDto.builder()
                .templateId(template.getId())
                .estimatedTime(template.getEstimatedTime())
                .connectedRoadmap(connectedRoadmap)
                .content(templateContent.getContent())
                .introduction(templateContent.getIntroduction())
                .relatedTemplate(relatedTemplate)
                .averageRating(averageRating)
                .teamCount(teamCount)
                .reviews(reviews)
                .user(user)
                .build();
    }
}
