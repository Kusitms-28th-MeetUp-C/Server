package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTemplateResponseDto {
    private Long templateId;
    private String title;

    public static CreateTemplateResponseDto of(Template template) {
        return CreateTemplateResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .build();
    }
}
