package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchTemplateResponseDto {
    List<SearchBaseTemplateResponseDto> templateList;

    public static SearchTemplateResponseDto of(List<SearchBaseTemplateResponseDto> templateList) {
        return SearchTemplateResponseDto.builder()
                .templateList(templateList)
                .build();
    }
}
