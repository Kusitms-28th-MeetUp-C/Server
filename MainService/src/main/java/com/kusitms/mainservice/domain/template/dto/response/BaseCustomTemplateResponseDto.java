package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.CustomTemplate;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseCustomTemplateResponseDto {
    private Long templateId;
    private String title;
    private TemplateType templateType;

    public static BaseCustomTemplateResponseDto of(CustomTemplate template){
        return BaseCustomTemplateResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .templateType(template.getTemplateType())
                .build();
    }
}
