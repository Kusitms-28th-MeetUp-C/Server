package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.team.dto.response.TeamTitleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetTeamForSaveTemplateResponseDto {
    private List<TeamTitleResponseDto> teamTitleResponseDtoList;

    public static GetTeamForSaveTemplateResponseDto of(List<TeamTitleResponseDto> teamTitleResponseDtoList) {

        return GetTeamForSaveTemplateResponseDto.builder()
                .teamTitleResponseDtoList(teamTitleResponseDtoList)
                .build();
    }
}
