package com.kusitms.mainservice.domain.template.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "template_contents")
public class TemplateContent {
    @Id
    private String id;
    private Long templateId;
    private String content;
    private String introduction;
}
