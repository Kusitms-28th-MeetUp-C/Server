package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TemplateDownloadDetailResponseDto {
    private Long templateId;
    private String templateName;
    private TemplateType templateType;
    private String content;

    public static TemplateDownloadDetailResponseDto of(Template template, String title) {
        return TemplateDownloadDetailResponseDto.builder()
                .templateId(template.getId())
                .templateName(template.getTitle())
                .templateType(template.getTemplateType())
                .content(title)
                .build();
    }
}
