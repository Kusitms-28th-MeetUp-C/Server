package com.kusitms.socketservice.domain.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(collection = "search_custom_step")
public class SearchCustomStep {
    private Long userId;
    private Long roadmapId;
    private String title;
    private RoadmapType roadmapType;
    private String relatedRoadmapTitle;
    private String relatedTemplateTitle;
    private boolean isOpend;
}
