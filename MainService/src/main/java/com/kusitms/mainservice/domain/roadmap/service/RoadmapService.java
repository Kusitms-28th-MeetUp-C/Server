package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.roadmap.dto.request.SearchRoadmapRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.*;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapDownloadRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapSpaceRepository;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.template.dto.response.SearchBaseTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.SearchTemplateResponseDto;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoadmapService {
    private final TemplateRepository templateRepository;
    private final RoadmapRepository roadmapRepository;
    private final RoadmapSpaceRepository roadmapSpaceRepository;
    private final RoadmapDownloadRepository roadmapDownloadRepository;
    public SearchRoadmapResponseDto searchRoadmapByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto){
        List<Roadmap> roadmapList = getRoadmapListByTitleAndRoadmapType(searchRoadmapRequestDto);
        List<SearchBaseRoadmapResponseDto> searchBaseRoadmapResponseDtos = createSearchRoadmapResponseDto(roadmapList);
        return SearchRoadmapResponseDto.of(searchBaseRoadmapResponseDtos);
    }

    public RoadmapDetailInfoResponseDto getRoadmapDetail(Long roadmapId){
        Roadmap roadmap = getRoadmapByRoadmapId(roadmapId);
        BaseRoadmapResponseDto baseRoadmapResponseDto = creatBaseRoadmapResponseDto(roadmap);
        DetailUserResponseDto detailUserResponseDto = createDetailUserResponseDto(roadmap.getUser());
        String introduction = "";
        List<SearchBaseRoadmapResponseDto> searchBaseRoadmapResponseDtoList = createSearchBaseRoadmapResponseDtoList(getRoadmapByRoadmapType(roadmap.getRoadmapType()));
        SearchRoadmapResponseDto searchRoadmapResponseDto = createSearchRoadmapResponseDto(searchBaseRoadmapResponseDtoList);
        int teamCount = getTeamCount(roadmap);
        return RoadmapDetailInfoResponseDto.of(roadmap, baseRoadmapResponseDto,detailUserResponseDto,introduction,searchRoadmapResponseDto,teamCount);
    }
    private Roadmap getRoadmapByRoadmapId(Long roadmapId){
        Optional<Roadmap> roadmap = roadmapRepository.findById(roadmapId);
        return roadmap.get();
    }
    private BaseRoadmapResponseDto creatBaseRoadmapResponseDto(Roadmap roadmap){
        List<RoadmapDetailResponseDto> roadmapDetailResponseDtoList = creatRoadmapDetailResponseDtoList(roadmap);
        return BaseRoadmapResponseDto.of(roadmap,roadmapDetailResponseDtoList);
    }
    private  List<RoadmapDetailResponseDto> creatRoadmapDetailResponseDtoList(Roadmap roadmap){
        return  RoadmapDetailResponseDto.listOf(roadmap);
    }
    private List<Roadmap> getRoadmapListByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto){
        if(searchRoadmapRequestDto.getRoadmapType()==null&&!(searchRoadmapRequestDto.getTitle()==null)){
            return getRoadmapByTitle(searchRoadmapRequestDto.getTitle());
        }
        if(!(searchRoadmapRequestDto.getRoadmapType()==null)&&searchRoadmapRequestDto.getTitle()==null) {
            return getRoadmapByRoadmapType(searchRoadmapRequestDto.getRoadmapType());
        }
        return getRoadmapByTitleAndRoadmapType(searchRoadmapRequestDto);
    }
    private DetailUserResponseDto createDetailUserResponseDto(User user){
        int templateNum = getTemplateCountByUser(user);
        int roadmapNum = getRoadmapCountByUser(user);
        return DetailUserResponseDto.of(user, templateNum,roadmapNum);
    }
    private int getTemplateCountByUser(User user) {
        return templateRepository.countByUser(user);
    }
    private int getRoadmapCountByUser(User user) {
        return roadmapRepository.countByUser(user);
    }
    private SearchRoadmapResponseDto createSearchRoadmapResponseDto(List<SearchBaseRoadmapResponseDto> searchBaseRoadmapResponseDtoList) {
        return SearchRoadmapResponseDto.of(searchBaseRoadmapResponseDtoList);
    }
    private int getTeamCount(Roadmap roadmap){
        int rc = roadmapDownloadRepository.countDownloadsByRoadmap(roadmap);
        return rc;
    }
    private List<Roadmap> getRoadmapByTitle(String title){
        return roadmapRepository.findByTitleContaining(title);
    }
    private List<Roadmap> getRoadmapByRoadmapType(RoadmapType roadmapType){
        if(RoadmapType.ALL.equals(roadmapType)) {
             return roadmapRepository.findAll();
        }
        else {
            return roadmapRepository.findByRoadmapType(roadmapType);
        }
    }
    private List<Roadmap> getRoadmapByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto){
        return roadmapRepository.findByTitleAndRoadmapType(searchRoadmapRequestDto.getTitle(),searchRoadmapRequestDto.getRoadmapType());
    }
    private List<SearchBaseRoadmapResponseDto> createSearchBaseRoadmapResponseDtoList(List<Roadmap> roadmapList){
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
