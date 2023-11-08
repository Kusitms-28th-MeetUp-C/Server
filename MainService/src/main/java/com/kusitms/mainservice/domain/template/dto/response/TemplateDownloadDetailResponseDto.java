package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.CustomTemplate;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TemplateDownloadDetailResponseDto {
    private Long templateId;
    private Long originalTemplateId;
    private String templateName;
    private TemplateType templateType;
    private String content;

    public static TemplateDownloadDetailResponseDto ofCustomTemplate(CustomTemplate template, Long originalTemplateId, String title) {
        return TemplateDownloadDetailResponseDto.builder()
                .templateId(template.getId())
                .originalTemplateId(originalTemplateId)
                .templateName(template.getTitle())
                .templateType(template.getTemplateType())
                .content(title)
                .build();
    }

    public static TemplateDownloadDetailResponseDto ofTemplate(Template template, Long originalTemplateId, String title) {
        return TemplateDownloadDetailResponseDto.builder()
                .templateId(template.getId())
                .originalTemplateId(originalTemplateId)
                .templateName(template.getTitle())
                .templateType(template.getTemplateType())
                .content(title)
                .build();
    }
}
