package com.kusitms.mainservice.domain.template.domain;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class TemplateData {

    private Template template;
    private TemplateContent templateContent;
}
