package com.kusitms.socketservice.domain.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(collection = "search_custom_roadmap")
public class SearchCustomRoadmap {
    private Long userId;
    private Long roadmapId;
    private String title;
    private RoadmapType roadmapType;
    private String relatedTeamTitle;
    private boolean isOpened;
}
