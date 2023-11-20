package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.roadmap.dto.request.SearchRoadmapRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.*;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapDownloadRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapSpaceRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kusitms.mainservice.domain.roadmap.domain.RoadmapType.getEnumRoadmapTypeFromStringRoadmapType;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoadmapService {
    private final RoadmapRepository roadmapRepository;
    private final RoadmapSpaceRepository roadmapSpaceRepository;
    private final RoadmapDownloadRepository roadmapDownloadRepository;
    private final TemplateRepository templateRepository;
    public Page<SearchBaseRoadmapResponseDto> searchRoadmapByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto, Pageable pageable){
        Page<Roadmap> roadmapList = getRoadmapListByTitleAndRoadmapType(searchRoadmapRequestDto, pageable);
        Page<SearchBaseRoadmapResponseDto> searchBaseRoadmapResponseDtos = createSearchBaseRoadmapResponseDtoPage(roadmapList);
        return searchBaseRoadmapResponseDtos;
    }

    public RoadmapDetailInfoResponseDto getRoadmapDetail(Long roadmapId){
        Roadmap roadmap = getRoadmapByRoadmapId(roadmapId);
        BaseRoadmapResponseDto baseRoadmapResponseDto = creatBaseRoadmapResponseDto(roadmap);
        DetailUserResponseDto detailUserResponseDto = createDetailUserResponseDto(roadmap.getUser());
        RoadmapDetailIntro roadmapDetailIntro = createRoadmapDetailIntro(roadmap);
        List<RoadmapDetailBaseRelateRoadmapDto> roadmapDetailRelateRoadmapDto = createRoadmapDetailRelateRoadmapDto(roadmap);
        return RoadmapDetailInfoResponseDto.of(roadmap, baseRoadmapResponseDto,detailUserResponseDto,roadmapDetailIntro,roadmapDetailRelateRoadmapDto);
    }
    private RoadmapDetailIntro createRoadmapDetailIntro(Roadmap roadmap){
        RoadmapDetailBaseIntro roadmapDetailBaseIntro = createRoadmapDetailBaseIntro(roadmap);

        return RoadmapDetailIntro.of(roadmap, roadmapDetailBaseIntro);
    }
    private RoadmapDetailBaseIntro createRoadmapDetailBaseIntro(Roadmap roadmap){
        int teamCount = getTeamCount(roadmap);
        return RoadmapDetailBaseIntro.of(roadmap, teamCount);
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
    private Page<Roadmap> getRoadmapListByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto, Pageable pageable){
        if(searchRoadmapRequestDto.getRoadmapType()==null&&!(searchRoadmapRequestDto.getTitle()==null)){
            return getRoadmapByTitle(searchRoadmapRequestDto.getTitle(),pageable);
        }
        if(!(searchRoadmapRequestDto.getRoadmapType()==null)&&searchRoadmapRequestDto.getTitle()==null) {
            return getRoadmapByRoadmapType(searchRoadmapRequestDto.getRoadmapType(), pageable);
        }
        return getRoadmapByTitleAndRoadmapType(searchRoadmapRequestDto,pageable);
    }
    private List<RoadmapDetailBaseRelateRoadmapDto> createRoadmapDetailRelateRoadmapDto(Roadmap roadmap) {
        List<Roadmap> roadmapList = getRoadmapListBySameCategoryAndId(roadmap);
        List<RoadmapDetailBaseRelateRoadmapDto> roadmapDetailBaseRelateRoadmapDtoList = createRoadmapDetailBaseRelateRoadmapDtoList(roadmapList);
        return roadmapDetailBaseRelateRoadmapDtoList;
    }
    private List<RoadmapDetailBaseRelateRoadmapDto> createRoadmapDetailBaseRelateRoadmapDtoList(List<Roadmap> roadmapList){
        return roadmapList.stream()
                .map(roadmap ->
                        RoadmapDetailBaseRelateRoadmapDto.of(
                                roadmap,
                                roadmap.getCount()//getTeamCount(roadmap)
                        ))
                .collect(Collectors.toList());
    }
    private int getTeamCount(Roadmap roadmap){
        int rc = roadmapDownloadRepository.countDownloadsByRoadmap(roadmap);
        return rc;
    }
    private Page<Roadmap> getRoadmapByTitle(String title, Pageable pageable){
        return roadmapRepository.findByTitleContainingOrderByCreateDateDesc(title,pageable);
    }
    private Page<Roadmap> getRoadmapByRoadmapType(String stringRoadmapType,Pageable pageable){
        RoadmapType roadmapType = getEnumRoadmapTypeFromStringRoadmapType(stringRoadmapType);
        if(RoadmapType.ALL.equals(roadmapType)) {
             return roadmapRepository.findAllByOrderByCreateDateDesc(pageable);
        }
        else {
            return roadmapRepository.findByRoadmapTypeOrderByCreateDateDesc(roadmapType,pageable);
        }
    }
    private Page<Roadmap> getRoadmapByTitleAndRoadmapType(SearchRoadmapRequestDto searchRoadmapRequestDto,Pageable pageable){
        String title = searchRoadmapRequestDto.getTitle();
        RoadmapType roadmapType = getEnumRoadmapTypeFromStringRoadmapType(searchRoadmapRequestDto.getRoadmapType());
        if(RoadmapType.ALL.equals(roadmapType)) {
            return roadmapRepository.findByTitleContainingOrderByCreateDateDesc(title, pageable);
        }
        else {
            Page<Roadmap> roadmapList = roadmapRepository.findByTitleContainingAndRoadmapTypeOrderByCreateDateDesc(title,roadmapType,pageable);
            return  roadmapList;

        }
    }
    public Page<SearchBaseRoadmapResponseDto> createSearchBaseRoadmapResponseDtoPage(Page<Roadmap> roadmapPage){
        return roadmapPage.map(roadmap ->
                        SearchBaseRoadmapResponseDto.of(
                                roadmap,
                                getRoadmapStep(roadmap)
                        )
                );
    }
    private int getRoadmapStep(Roadmap roadmap){
        List<RoadmapSpace> roadmapSpaceList = roadmapSpaceRepository.findByRoadmapId(roadmap.getId());
        return roadmapSpaceList.size();
    }
    private List<Roadmap> getRoadmapListBySameCategoryAndId(Roadmap roadmap){
        List<Roadmap> roadmapList = roadmapRepository.findTop6ByRoadmapTypeAndIdNot(roadmap.getRoadmapType(), roadmap.getId());
       return roadmapList;
    }

    private DetailUserResponseDto createDetailUserResponseDto(User user) {
        int templateNum = getTemplateCountByUser(user);
        int roadmapNum = getRoadmapCountByUser(user);
        return DetailUserResponseDto.of(user, templateNum, roadmapNum);
    }

    private int getTemplateCountByUser(User user) {
        return templateRepository.countByUser(user);
    }

    private int getRoadmapCountByUser(User user) {
        return roadmapRepository.countByUser(user);
    }
}
