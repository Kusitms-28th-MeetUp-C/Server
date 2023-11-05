package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.template.domain.Rating;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.UserAuthResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TemplateDetailResponseDto {
    private Long templateId;
    private int templateNum;
    private int roadmapNum;
    private int estimatedTime;
    private List<RoadmapTitleResponseDto> connectedRoadmap;
    private int upload;
    private String content;
    private String introduction;
    private List<SearchBaseTemplateResponseDto> relatedTemplate;
    private Rating rating;
    private int teamCount;
    private List<ReviewContentResponseDto> reviews;
    private TemplateDetailUserResponseDto user;


}
