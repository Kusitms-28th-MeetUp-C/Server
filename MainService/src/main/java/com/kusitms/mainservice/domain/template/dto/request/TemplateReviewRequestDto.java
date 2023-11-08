package com.kusitms.mainservice.domain.template.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TemplateReviewRequestDto {
    private Long meetingId;
    private String content;
}
