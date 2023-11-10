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
    private String introduction;
    private Map<Long, List<Map<String, String>>> templateContentResponseDtoList;

    public static TemplateContentListResponseDto of(Template template,Map<Long, List<Map<String, String>>> templateContentResponseDtoList){
        return TemplateContentListResponseDto.builder()
                .introduction(template.getIntroduction())
                .templateContentResponseDtoList(templateContentResponseDtoList)
                .build();
    }
}
