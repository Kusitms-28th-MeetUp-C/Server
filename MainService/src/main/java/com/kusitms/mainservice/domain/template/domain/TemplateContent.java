package com.kusitms.mainservice.domain.template.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "template_contents")
public class TemplateContent {
    @Id
    private String id;
    private Long templateId;
    private Long agendaNum;
    private String agenda;
    private String content;
}
