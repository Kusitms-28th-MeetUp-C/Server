package com.kusitms.socketservice.domain.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Document(collection = "search_user_template")
public class SearchUserTemplate {
    private Long templateId;
    private String userId;
    private String title;
    private String relatedTeamTitle;
    private TemplateType templateType;
    private boolean isOpened;
}
