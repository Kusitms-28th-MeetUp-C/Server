package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserRoadmapResponseDto {
    private List<BaseRoadmapResponseDto> roadmapList;

    public static UserRoadmapResponseDto of(List<BaseRoadmapResponseDto> roadmapList){
        return UserRoadmapResponseDto.builder()
                .roadmapList(roadmapList)
                .build();
    }
}
