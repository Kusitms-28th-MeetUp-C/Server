package com.kusitms.mainservice.domain.team.dto.response;

import com.kusitms.mainservice.domain.team.domain.TeamSpace;
import com.kusitms.mainservice.domain.team.domain.TeamSpaceType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class TeamSpaceResponseDto {
    private TeamSpaceType spaceType;
    private String url;

    public static List<TeamSpaceResponseDto> listOf(List<TeamSpace> teamSpaceList) {
        return teamSpaceList.stream()
                .map(TeamSpaceResponseDto::of)
                .collect(Collectors.toList());
    }

    public static TeamSpaceResponseDto of(TeamSpace teamSpace) {
        return TeamSpaceResponseDto.builder()
                .spaceType(teamSpace.getTeamSpaceType())
                .url(teamSpace.getUrl())
                .build();
    }
}
