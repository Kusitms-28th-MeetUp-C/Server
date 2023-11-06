package com.kusitms.mainservice.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamListResponseDto {
    private List<TeamListElementsResponseDto> teamList;

    public static TeamListResponseDto of(List<TeamListElementsResponseDto> teamList) {
        return TeamListResponseDto.builder()
                .teamList(teamList)
                .build();
    }
}
