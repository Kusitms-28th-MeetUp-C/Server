package com.kusitms.mainservice.domain.template.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@Document(collection = "custom_template_contents")
public class CustomTemplateContent {
    private Long templateId;
    private String content;

    public void updateCustomTemplateContent(String content) {
        this.content = content;
    }

    public static CustomTemplateContent createCustomTemplateContent(Long templateId, String content) {
        return CustomTemplateContent.builder()
                .templateId(templateId)
                .content(content)
                .build();
    }
}
