package com.kusitms.mainservice.domain.template.domain;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "template_contents")
public class TemplateContent {
    private Long template_id;
    private String content;
}
