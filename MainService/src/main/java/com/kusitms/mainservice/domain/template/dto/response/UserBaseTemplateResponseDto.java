package com.kusitms.mainservice.domain.template.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserBaseTemplateResponseDto {
    List<BaseTemplateResponseDto> templateList;

    public static UserBaseTemplateResponseDto of(List<BaseTemplateResponseDto> templateList) {
        return UserBaseTemplateResponseDto.builder()
                .templateList(templateList)
                .build();
    }
}
