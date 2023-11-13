package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RoadmapDetailInfoResponseDto {
    private RoadmapType roadmapType;
    private BaseRoadmapResponseDto baseRoadmapResponseDto;
    private DetailUserResponseDto detailUserResponseDto;
    private RoadmapDetailIntro roadmapDetailIntro;
    private List<RoadmapDetailBaseRelateRoadmapDto> roadmapDetailRelateRoadmapDto;


    public static RoadmapDetailInfoResponseDto of(Roadmap roadmap, BaseRoadmapResponseDto baseRoadmapResponseDto, DetailUserResponseDto detailUserResponseDto, RoadmapDetailIntro roadmapDetailIntro, List<RoadmapDetailBaseRelateRoadmapDto> roadmapDetailRelateRoadmapDto){
        return RoadmapDetailInfoResponseDto.builder()
                .roadmapType(roadmap.getRoadmapType())
                .baseRoadmapResponseDto(baseRoadmapResponseDto)
                .detailUserResponseDto(detailUserResponseDto)
                .roadmapDetailIntro(roadmapDetailIntro)
                .roadmapDetailRelateRoadmapDto(roadmapDetailRelateRoadmapDto)
                .build();
    }



}
