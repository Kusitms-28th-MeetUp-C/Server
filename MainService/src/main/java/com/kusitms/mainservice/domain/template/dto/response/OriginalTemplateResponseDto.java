package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.user.dto.response.MakerResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OriginalTemplateResponseDto {
    private Long templateId;
    private String templateName;
    private TemplateType templateType;
    private String content;
    private MakerResponseDto makerInfo;

    public static OriginalTemplateResponseDto of(Template template, String content, MakerResponseDto makerResponseDto) {
        return OriginalTemplateResponseDto.builder()
                .templateId(template.getId())
                .templateName(template.getTitle())
                .templateType(template.getTemplateType())
                .content(content)
                .makerInfo(makerResponseDto)
                .build();
    }
}
