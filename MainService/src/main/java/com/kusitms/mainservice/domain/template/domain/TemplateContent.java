package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.template.dto.request.UpdateTemplateRequestDto;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@Document(collection = "template_contents")
public class TemplateContent {
    @Id
    private String id;
    @Indexed(unique = true)
    @Field(name = "template_id")
    private Long templateId;
    private String content;

    public static TemplateContent createTemplateContent(Long templateId, String content){
        return TemplateContent.builder()
                .templateId(templateId)
                .content(content)
                .build();
    }
}
