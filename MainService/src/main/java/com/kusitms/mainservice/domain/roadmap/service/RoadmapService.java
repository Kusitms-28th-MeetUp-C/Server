package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.dto.request.SearchRoadmapRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.SearchBaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.SearchRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapSpaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.mysql.cj.MysqlType.NULL;
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final RoadmapSpaceRepository roadmapSpaceRepository;
    public SearchRoadmapResponseDto searchRoadmapByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto){
        List<Roadmap> roadmapList = getRoadmapListByTitleAndRoadmapType(searchRoadmapRequestDto);
        List<SearchBaseRoadmapResponseDto> searchBaseRoadmapResponseDtos = createSearchRoadmapResponseDto(roadmapList);
        return SearchRoadmapResponseDto.of(searchBaseRoadmapResponseDtos);
    }

    private List<Roadmap> getRoadmapListByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto){
        if(searchRoadmapRequestDto.getRoadmapType()==null&&!(searchRoadmapRequestDto.getTitle()==null)){
            return roadmapRepository.findByTitleContaining(searchRoadmapRequestDto.getTitle());
        }
        if(!(searchRoadmapRequestDto.getRoadmapType()==null)&&searchRoadmapRequestDto.getTitle()==null) {
            return roadmapRepository.findByRoadmapType(searchRoadmapRequestDto.getRoadmapType());
        }
        return roadmapRepository.findByTitleAndRoadmapType(searchRoadmapRequestDto.getTitle(),searchRoadmapRequestDto.getRoadmapType());
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
