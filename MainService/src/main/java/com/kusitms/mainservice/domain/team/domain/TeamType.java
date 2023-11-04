package com.kusitms.mainservice.domain.team.domain;

import com.kusitms.mainservice.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.mainservice.global.error.ErrorCode.INVALID_TEAM_TYPE;

@RequiredArgsConstructor
@Getter
public enum TeamType {
    CONFERENCE("conference"),
    CLUB("club"),
    SIDE_PROJECT("side_project"),
    UNIVERSITY_TEAM_PLAY("university_team_play"),
    VIDEO("video"),
    MARKING("marking");

    private final String stringTeamType;

    public static TeamType getEnumTeamTypeFromStringTeamType(String stringTeamType) {
        return Arrays.stream(values())
                .filter(teamType -> teamType.stringTeamType.equals(stringTeamType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TEAM_TYPE));
    }
}
