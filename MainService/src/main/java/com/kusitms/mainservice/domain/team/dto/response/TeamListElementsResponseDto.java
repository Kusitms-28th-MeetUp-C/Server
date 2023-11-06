package com.kusitms.mainservice.domain.team.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.BaseRoadmapResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamListElementsResponseDto {
    private TeamResponseDto teamInfo;
    private BaseRoadmapResponseDto teamRoadmap;

    public static TeamListElementsResponseDto of(TeamResponseDto teamInfo, BaseRoadmapResponseDto teamRoadmap) {
        return TeamListElementsResponseDto.builder()
                .teamInfo(teamInfo)
                .teamRoadmap(teamRoadmap)
                .build();
    }
}
