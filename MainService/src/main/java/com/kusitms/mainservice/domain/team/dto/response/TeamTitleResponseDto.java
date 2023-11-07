package com.kusitms.mainservice.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamTitleResponseDto {
    private String title;

    public static TeamTitleResponseDto of(String title){
        return TeamTitleResponseDto.builder()
                .title(title)
                .build();
    }
}
