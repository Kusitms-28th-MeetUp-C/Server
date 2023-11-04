package com.kusitms.mainservice.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamRequestDto {
    private String title;
    private String introduction;
    private String teamType;
    private List<TeamSpaceRequestDto> spaceList;
}
