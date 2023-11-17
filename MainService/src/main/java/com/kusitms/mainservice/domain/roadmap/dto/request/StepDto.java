package com.kusitms.mainservice.domain.roadmap.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StepDto {
    private String stepTitle;
    private List<Long> templateIdList;
}
