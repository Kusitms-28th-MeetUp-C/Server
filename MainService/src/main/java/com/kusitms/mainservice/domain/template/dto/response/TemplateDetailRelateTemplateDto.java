package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TemplateDetailRelateTemplateDto {
    List<TemplateDetailBaseRelateDto> templateList;

    public static TemplateDetailRelateTemplateDto of(List<TemplateDetailBaseRelateDto> templateList){
        return TemplateDetailRelateTemplateDto.builder()
                .templateList(templateList)
                .build();
    }
}
