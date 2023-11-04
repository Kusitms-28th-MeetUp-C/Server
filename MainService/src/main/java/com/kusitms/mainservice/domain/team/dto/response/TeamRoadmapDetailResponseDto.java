package com.kusitms.mainservice.domain.team.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.team.domain.TeamType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamRoadmapDetailResponseDto {
    private Long teamId;
    private String title;
    private String introduction;
    private TeamType teamType;
    private List<TeamSpaceResponseDto> teamSpaceList;
    private CustomRoadmapDetailResponseDto roadmap;

    public static TeamRoadmapDetailResponseDto of(Team team, List<TeamSpaceResponseDto> teamSpaceList, CustomRoadmapDetailResponseDto roadmap) {
        return TeamRoadmapDetailResponseDto.builder()
                .teamId(team.getId())
                .title(team.getTitle())
                .introduction(team.getIntroduction())
                .teamType(team.getTeamType())
                .teamSpaceList(teamSpaceList)
                .roadmap(roadmap)
                .build();
    }
}
