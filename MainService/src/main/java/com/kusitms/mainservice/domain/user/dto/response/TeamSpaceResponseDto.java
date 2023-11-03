package com.kusitms.mainservice.domain.user.dto.response;


import com.kusitms.mainservice.domain.user.domain.TeamSpace;
import com.kusitms.mainservice.domain.user.domain.TeamSpaceType;
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
