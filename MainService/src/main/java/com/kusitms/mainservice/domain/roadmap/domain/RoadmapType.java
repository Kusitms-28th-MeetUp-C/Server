package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.mainservice.global.error.ErrorCode.INVALID_ROADMAP_TYPE;

@RequiredArgsConstructor
@Getter
public enum RoadmapType {
    IT("it"),
    SURVEY_DATA_ANALYSIS("survey_data_analysis"),
    CORPORATE_ANALYSIS("corporate_analysis"),
    PT("pt"),
    DESIGN("design"),
    MARKETING("marketing"),
    ETC("etc");

    private final String stringRoadmapType;

    public static RoadmapType getEnumPeriodFromStringPeriod(String stringRoadmapType) {
        return Arrays.stream(values())
                .filter(period -> period.stringRoadmapType.equals(stringRoadmapType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_ROADMAP_TYPE));
    }

}
