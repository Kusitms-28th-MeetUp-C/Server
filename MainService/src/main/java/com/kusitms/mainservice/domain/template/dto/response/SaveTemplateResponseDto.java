package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.team.dto.response.TeamTitleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SaveTemplateResponseDto {
    private List<TeamTitleResponseDto> teamTitleResponseDtoList;

    public static SaveTemplateResponseDto of(List<TeamTitleResponseDto> teamTitleResponseDtoList){

        return SaveTemplateResponseDto.builder()
                .teamTitleResponseDtoList(teamTitleResponseDtoList)
                .build();
    }
}
