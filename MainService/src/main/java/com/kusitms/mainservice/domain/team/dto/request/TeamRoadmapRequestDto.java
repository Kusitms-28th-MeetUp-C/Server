package com.kusitms.mainservice.domain.team.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamRoadmapRequestDto {
    private Long roadmapId;
    private Long teamId;
}
