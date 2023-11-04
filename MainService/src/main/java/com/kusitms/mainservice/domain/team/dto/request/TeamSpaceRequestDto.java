package com.kusitms.mainservice.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamSpaceRequestDto {
    private String spaceType;
    private String url;
}