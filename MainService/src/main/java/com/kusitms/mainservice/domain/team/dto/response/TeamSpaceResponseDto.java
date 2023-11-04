package com.kusitms.mainservice.domain.team.dto.response;


import com.kusitms.mainservice.domain.team.domain.TeamSpace;
import com.kusitms.mainservice.domain.team.domain.TeamSpaceType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamSpaceResponseDto {
    private Long spaceId;
    private TeamSpaceType spaceType;
    private String url;

    public static TeamSpaceResponseDto of(TeamSpace teamSpace) {
        return TeamSpaceResponseDto.builder()
                .spaceId(teamSpace.getId())
                .spaceType(teamSpace.getTeamSpaceType())
                .url(teamSpace.getUrl())
                .build();
    }
}
