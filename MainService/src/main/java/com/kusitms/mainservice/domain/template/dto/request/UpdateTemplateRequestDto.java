package com.kusitms.mainservice.domain.template.dto.request;

import com.kusitms.mainservice.domain.template.domain.TemplateType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateTemplateRequestDto {
    private Long templateId;
    private String title;
    private String content;
    private String templateType;
    private int estimatedTime;
    private String introduction;
}
