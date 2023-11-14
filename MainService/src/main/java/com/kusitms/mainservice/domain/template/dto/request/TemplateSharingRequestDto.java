package com.kusitms.mainservice.domain.template.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TemplateSharingRequestDto {
    private String title;
    private String introduction;
    private String content;
    private String templateType;
    private int estimatedTime;
}
