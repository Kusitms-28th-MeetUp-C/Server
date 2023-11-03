package com.kusitms.mainservice.domain.user.domain;

import com.kusitms.mainservice.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.mainservice.global.error.ErrorCode.INVALID_TEAM_TYPE;

@RequiredArgsConstructor
@Getter
public enum TeamSpaceType {
    NOTION("notion"),
    FIGMA("figma"),
    JIRA("jira");

    private final String stringTeamSpaceType;

    public static TeamSpaceType getEnumSpaceTypeFromStringSpaceType(String stringTeamSpaceType) {
        return Arrays.stream(values())
                .filter(teamSpaceType -> teamSpaceType.stringTeamSpaceType.equals(stringTeamSpaceType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TEAM_TYPE));
    }
}
