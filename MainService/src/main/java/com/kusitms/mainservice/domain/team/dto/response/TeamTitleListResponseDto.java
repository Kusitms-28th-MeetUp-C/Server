package com.kusitms.mainservice.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamTitleListResponseDto {
    private List<TeamTitleElementResponseDto> teamList;

    public static TeamTitleListResponseDto of(List<TeamTitleElementResponseDto> teamList) {
        return TeamTitleListResponseDto.builder()
                .teamList(teamList)
                .build();
    }
}
