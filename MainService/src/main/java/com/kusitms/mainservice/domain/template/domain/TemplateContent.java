package com.kusitms.mainservice.domain.template.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "template_contents")
public class TemplateContent {
    private Long template_id;
    private String content;
}
