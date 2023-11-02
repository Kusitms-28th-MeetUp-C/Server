package com.kusitms.mainservice.domain.roadmap.dto.response;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RelatedRoadmapResponseDto {
    private Long roadmapId;
    private String title;

    public static RelatedRoadmapResponseDto of(Roadmap roadmap) {
        return RelatedRoadmapResponseDto.builder()
                .roadmapId(roadmap.getId())
                .title(roadmap.getTitle())
                .build();
    }
}
