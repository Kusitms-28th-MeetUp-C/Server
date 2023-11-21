package com.kusitms.mainservice.domain.template.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@Document(collection = "custom_template_contents")
public class CustomTemplateContent {
    @Id
    @Field(name = "_id")
    private String id;

    @Field(name = "template_id")
    @Indexed(unique = true)
    private Long templateId;

    @Field(name = "content")
    private String content;
    public void updateCustomTemplateContent(String content){
        this.content=content;
    }
    public static CustomTemplateContent createCustomTemplateContent(Long templateId, String content){
        return CustomTemplateContent.builder()
                .templateId(templateId)
                .content(content)
                .build();
    }
}
