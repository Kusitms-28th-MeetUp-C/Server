package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RoadmapDetailInfoResponseDto {
    private Long roadmapId;
    private String roadmapType;
    private String title;
    private RoadmapDetailIntro roadmapIntro;
    private BaseRoadmapResponseDto roadmapData;
    private List<RoadmapDetailBaseRelateRoadmapDto> relatedRoadmap;
    private DetailUserResponseDto user;


    public static RoadmapDetailInfoResponseDto of(Roadmap roadmap, BaseRoadmapResponseDto baseRoadmapResponseDto, DetailUserResponseDto detailUserResponseDto, RoadmapDetailIntro roadmapDetailIntro, List<RoadmapDetailBaseRelateRoadmapDto> roadmapDetailRelateRoadmapDto) {
        return RoadmapDetailInfoResponseDto.builder()
                .roadmapId(roadmap.getId())
                .roadmapType(roadmap.getRoadmapType().toString())
                .title(roadmap.getTitle())
                .roadmapData(baseRoadmapResponseDto)
                .user(detailUserResponseDto)
                .roadmapIntro(roadmapDetailIntro)
                .relatedRoadmap(roadmapDetailRelateRoadmapDto)
                .build();
    }


}
