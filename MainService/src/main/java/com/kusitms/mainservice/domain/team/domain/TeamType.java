package com.kusitms.mainservice.domain.team.domain;

import com.kusitms.mainservice.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.mainservice.global.error.ErrorCode.INVALID_TEAM_TYPE;

@RequiredArgsConstructor
@Getter
public enum TeamType {
    IT("it"),
    SURVEY_DATA_ANALYSIS("survey_data_analysis"),
    CORPORATE_ANALYSIS("corporate_analysis"),
    PT("pt"),
    DESIGN("design"),
    MARKETING("marketing"),
    ETC("etc"),
    TEAM("team"),
    CLUB("club"),
    VIDEO("video");

    private final String stringTeamType;

    public static TeamType getEnumTeamTypeFromStringTeamType(String stringTeamType) {
        return Arrays.stream(values())
                .filter(teamType -> teamType.stringTeamType.equals(stringTeamType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TEAM_TYPE));
    }
}
