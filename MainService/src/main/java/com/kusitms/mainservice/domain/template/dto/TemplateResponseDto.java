package com.kusitms.mainservice.domain.template.dto;

import com.kusitms.mainservice.domain.template.domain.TemplateType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TemplateResponseDto {
    private Long id;
    private String content;
    private String title;

    @Enumerated(EnumType.STRING)
    private TemplateType template_type;
}
