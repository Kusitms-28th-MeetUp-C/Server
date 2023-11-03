package com.kusitms.mainservice.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamRoadmapRequestDto {
    private Long roadmapId;
    private Long teamId;
}
