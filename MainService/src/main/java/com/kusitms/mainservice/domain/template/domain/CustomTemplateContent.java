package com.kusitms.mainservice.domain.template.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "custom_template_contents")
public class CustomTemplateContent {
    @Id
    private String id;
    private Long templateId;
    private String content;
    private String introduction;
}
