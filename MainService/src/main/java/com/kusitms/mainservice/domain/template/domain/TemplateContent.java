package com.kusitms.mainservice.domain.template.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "template_contents")
public class TemplateContent {
    private Long templateId;
    private String content;

    public static TemplateContent createTemplateContent(Long templateId, String content) {
        return TemplateContent.builder()
                .templateId(templateId)
                .content(content)
                .build();
    }
}
