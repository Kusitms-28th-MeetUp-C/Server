package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class PageResponse {
    private Page<SearchBaseTemplateResponseDto> List;

    public static PageResponse of(Page<SearchBaseTemplateResponseDto> List){
        return PageResponse.builder()
                .List(List)
                .build();
    }
}
