package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(collection = "search_user_template")
public class SearchUserTemplate {
    private Long templateId;
    private String userId;
    private String title;
    private String relatedTeamTitle;
    private TemplateType templateType;

    public static SearchUserTemplate of(User user, CustomTemplate customTemplate, String relatedTeamTitle) {
        return SearchUserTemplate.builder()
                .userId(user.getId().toString())
                .templateId(customTemplate.getId())
                .title(customTemplate.getTitle())
                .relatedTeamTitle(relatedTeamTitle)
                .templateType(customTemplate.getTemplateType())
                .build();
    }

    public void updateTeamTitle(String title) {
        this.relatedTeamTitle = title;
    }
}
