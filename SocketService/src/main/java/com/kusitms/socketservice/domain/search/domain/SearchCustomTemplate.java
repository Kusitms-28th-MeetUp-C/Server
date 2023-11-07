package com.kusitms.socketservice.domain.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(collection = "search_custom_template")
public class SearchCustomTemplate {
    private Long userId;
    private Long templateId;
    private String title;
    private String relatedTeamTitle;
    private String relatedRoadmapTitle;
    private TemplateType templateType;
    private boolean isOpened;
}
