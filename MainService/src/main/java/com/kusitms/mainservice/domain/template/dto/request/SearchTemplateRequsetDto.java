package com.kusitms.mainservice.domain.template.dto.request;

import com.kusitms.mainservice.domain.template.domain.TemplateType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchTemplateRequsetDto {
    private String title;
    private String templateType;
}
