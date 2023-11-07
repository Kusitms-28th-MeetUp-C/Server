package com.kusitms.socketservice.domain.search.domain;

import com.kusitms.socketservice.global.error.httpException.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.socketservice.global.error.ErrorCode.INVALID_ROADMAP_TYPE;

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

    public static RoadmapType getEnumRoadmapTypeFromStringRoadmapType(String stringRoadmapType) {
        return Arrays.stream(values())
                .filter(roadmapType -> roadmapType.stringRoadmapType.equals(stringRoadmapType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_ROADMAP_TYPE));
    }

}
