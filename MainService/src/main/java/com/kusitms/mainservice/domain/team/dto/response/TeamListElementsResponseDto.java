package com.kusitms.mainservice.domain.team.dto.response;

import com.kusitms.mainservice.domain.roadmap.dto.response.BaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapDetailResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamListElementsResponseDto {
    private TeamResponseDto teamInfo;
    private CustomRoadmapDetailResponseDto teamRoadmap;

    public static TeamListElementsResponseDto of(TeamResponseDto teamInfo, CustomRoadmapDetailResponseDto teamRoadmap) {
        return TeamListElementsResponseDto.builder()
                .teamInfo(teamInfo)
                .teamRoadmap(teamRoadmap)
                .build();
    }
}
