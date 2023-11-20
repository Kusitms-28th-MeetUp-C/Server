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
    private boolean isOpened;

    public static SearchUserTemplate of(User user, Long templateId, String title,
                                        String relatedTeamTitle, TemplateType templateType,
                                        boolean isOpened){
        return SearchUserTemplate.builder()
                .userId(user.getId().toString())
                .templateId(templateId)
                .title(title)
                .relatedTeamTitle(relatedTeamTitle)
                .templateType(templateType)
                .isOpened(isOpened)
                .build();
    }
}
