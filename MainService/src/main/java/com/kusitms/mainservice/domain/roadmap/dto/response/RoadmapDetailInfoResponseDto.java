package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoadmapDetailInfoResponseDto {
    private RoadmapType roadmapType;
    private BaseRoadmapResponseDto baseRoadmapResponseDto;
    private DetailUserResponseDto detailUserResponseDto;
    private String introduction;
    private SearchRoadmapResponseDto searchRoadmapResponseDto;
    private String date;
    private int teamCount;

    public static RoadmapDetailInfoResponseDto of(Roadmap roadmap, BaseRoadmapResponseDto baseRoadmapResponseDto, DetailUserResponseDto detailUserResponseDto, String introduction, SearchRoadmapResponseDto searchRoadmapResponseDto, int teamCount){
        return RoadmapDetailInfoResponseDto.builder()
                .roadmapType(roadmap.getRoadmapType())
                .baseRoadmapResponseDto(baseRoadmapResponseDto)
                .detailUserResponseDto(detailUserResponseDto)
                .introduction(introduction)
                .searchRoadmapResponseDto(searchRoadmapResponseDto)
                .teamCount(teamCount)
                .build();
    }



}
