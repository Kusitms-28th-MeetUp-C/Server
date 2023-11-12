package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
@Getter
public class TemplateContentListResponseDto {
    private List<TemplateContent> templateContentList;

    public static TemplateContentListResponseDto of(List<TemplateContent> templateContentList){
        return TemplateContentListResponseDto.builder()
                .templateContentList(templateContentList)
                .build();
    }
}
