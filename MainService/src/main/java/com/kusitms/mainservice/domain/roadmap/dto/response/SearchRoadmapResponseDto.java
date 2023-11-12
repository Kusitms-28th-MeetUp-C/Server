package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class SearchRoadmapResponseDto {
    Page<SearchBaseRoadmapResponseDto> roadmapPage;

    public static SearchRoadmapResponseDto of(Page<SearchBaseRoadmapResponseDto> roadmapPage){
        return SearchRoadmapResponseDto.builder()
                .roadmapPage(roadmapPage)
                .build();
    }
}
