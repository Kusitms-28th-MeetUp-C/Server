package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TemplateTitleResponseDto {
    private Long templateId;
    private String title;

    public static TemplateTitleResponseDto of(Template template) {
        return TemplateTitleResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .build();
    }
}
