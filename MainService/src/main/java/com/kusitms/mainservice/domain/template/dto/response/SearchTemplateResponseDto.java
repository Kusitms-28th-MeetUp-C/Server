package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class SearchTemplateResponseDto {
    Page<SearchBaseTemplateResponseDto> templateList;

    public static SearchTemplateResponseDto of(Page<SearchBaseTemplateResponseDto> templateList) {
        return SearchTemplateResponseDto.builder()
                .templateList(templateList)
                .build();
    }
}
