package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.dto.request.SearchRoadmapRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.SearchBaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.SearchRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapSpaceRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RoadmapService {

    private RoadmapRepository roadmapRepository;
    private RoadmapSpaceRepository roadmapSpaceRepository;
    public SearchRoadmapResponseDto searchRoadmapByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto){
        List<Roadmap> roadmapList = getRoadmapListByTitleAndRoadmapType(searchRoadmapRequestDto);
        List<SearchBaseRoadmapResponseDto> searchBaseRoadmapResponseDtos = createSearchRoadmapResponseDto(roadmapList);
        return SearchRoadmapResponseDto.of(searchBaseRoadmapResponseDtos)
    }

    private List<Roadmap> getRoadmapListByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto){
        List<Roadmap> roadmapList = roadmapRepository.findByTitleAndRoadmapType(searchRoadmapRequestDto.getTitle(),searchRoadmapRequestDto.getRoadmapType());
        return roadmapList;
    }
    private List<SearchBaseRoadmapResponseDto> createSearchRoadmapResponseDto(List<Roadmap> roadmapList){
        return roadmapList.stream()
                .map(roadmap ->
                        SearchBaseRoadmapResponseDto.of(
                                roadmap,
                                getRoadmapStep(roadmap)))
                .collect(Collectors.toList());
    }
    private int getRoadmapStep(Roadmap roadmap){
        List<RoadmapSpace> roadmapSpaceList = roadmapSpaceRepository.findByRoadmapId(roadmap.getId());
        return roadmapSpaceList.size();
    }
}