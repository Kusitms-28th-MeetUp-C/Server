package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RelatedTemplateResponseDto {
    private Long templateId;
    private String title;

    public static RelatedTemplateResponseDto of(Template template) {
        return RelatedTemplateResponseDto.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .build();
    }
}
