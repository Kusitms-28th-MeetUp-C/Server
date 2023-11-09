package com.kusitms.mainservice.domain.roadmap.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchRoadmapResponseDto {
    List<SearchBaseRoadmapResponseDto> roadmapList;
}
