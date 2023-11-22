package com.kusitms.socketservice.domain.search.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateRequestDto {
    private String title;
    private Long templateId;
}
