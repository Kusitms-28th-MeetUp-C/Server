package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Builder
@Getter
public class SearchRoadmapResponseDto {
    private Page<SearchBaseRoadmapResponseDto> roadmapPage;

    public static SearchRoadmapResponseDto of(Page<SearchBaseRoadmapResponseDto> roadmapPage) {
        return SearchRoadmapResponseDto.builder()
                .roadmapPage(roadmapPage)
                .build();
    }
}
