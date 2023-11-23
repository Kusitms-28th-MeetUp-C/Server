package com.kusitms.mainservice.domain.team.dto.response;

import com.kusitms.mainservice.domain.team.domain.Team;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class TeamTitleElementResponseDto {
    private Long teamId;
    private String title;

    public static List<TeamTitleElementResponseDto> listOf(List<Team> teamList) {
        return teamList.stream()
                .map(TeamTitleElementResponseDto::of)
                .collect(Collectors.toList());
    }

    public static TeamTitleElementResponseDto of(Team team) {
        return TeamTitleElementResponseDto.builder()
                .teamId(team.getId())
                .title(team.getTitle())
                .build();
    }
}
