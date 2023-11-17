package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTemplateResponseDto {
    private Long templateId;
    public static CreateTemplateResponseDto of(Long templateId){
        return CreateTemplateResponseDto.builder()
                .templateId(templateId)
                .build();
    }
}
