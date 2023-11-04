package com.kusitms.mainservice.domain.team.dto.response;


import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.team.domain.TeamType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamResponseDto {
    private Long teamId;
    private String title;
    private TeamType teamType;
    private String introduction;
    private List<TeamSpaceResponseDto> spaceList;

    public static TeamResponseDto of(Team team, List<TeamSpaceResponseDto> spaceList) {
        return TeamResponseDto.builder()
                .teamId(team.getId())
                .title(team.getTitle())
                .teamType(team.getTeamType())
                .introduction(team.getIntroduction())
                .spaceList(spaceList)
                .build();
    }
}
