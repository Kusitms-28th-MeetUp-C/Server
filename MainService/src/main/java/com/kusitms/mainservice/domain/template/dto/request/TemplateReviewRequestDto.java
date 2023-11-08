package com.kusitms.mainservice.domain.template.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TemplateReviewRequestDto {
    private Long meetingId;
    private int rating;
    private String content;
}
