package com.kusitms.socketservice.domain.search.service;

import com.kusitms.socketservice.domain.search.domain.*;
import com.kusitms.socketservice.domain.search.dto.request.SearchRequestDto;
import com.kusitms.socketservice.domain.search.dto.response.SearchResultElementResponseDto;
import com.kusitms.socketservice.domain.search.dto.response.SearchResultResponseDto;
import com.kusitms.socketservice.domain.search.repository.SearchCustomRoadmapRepository;
import com.kusitms.socketservice.domain.search.repository.SearchCustomStepRepository;
import com.kusitms.socketservice.domain.search.repository.SearchCustomTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kusitms.socketservice.domain.search.domain.RoadmapType.getEnumRoadmapTypeFromStringRoadmapType;
import static com.kusitms.socketservice.domain.search.domain.SearchType.getEnumSearchTypeFromString;
import static com.kusitms.socketservice.domain.search.domain.SubSearchType.getEnumSubSearchTypeFromString;
import static com.kusitms.socketservice.domain.search.domain.TemplateType.getEnumTemplateTypeFromStringTemplateType;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchService {
    private final SearchCustomTemplateRepository searchCustomTemplateRepository;
    private final SearchCustomRoadmapRepository searchCustomRoadmapRepository;
    private final SearchCustomStepRepository searchCustomStepRepository;

    public SearchResultResponseDto getSearchResult(Long userId, SearchRequestDto searchRequestDto) {
        SearchType searchType = getEnumSearchTypeFromString(searchRequestDto.getSearchType());
        List<SearchResultElementResponseDto> searchCustomTemplateList
                = getSearchResultOfCustomTemplateList(userId, searchRequestDto.getSearchString(), searchType, searchRequestDto.getSubSearchType());
        List<SearchResultElementResponseDto> searchCustomRoadmpList
                = getSearchResultOfCustomRoadmapList(userId, searchRequestDto.getSearchString(), searchType, searchRequestDto.getSubSearchType());
        List<SearchResultElementResponseDto> searchCustomStepList
                = getSearchCustomStepOfRoadmapStepList(userId, searchRequestDto.getSearchString(), searchType, searchRequestDto.getSubSearchType());
        return SearchResultResponseDto.of(joinSearchResultList(searchCustomTemplateList, searchCustomRoadmpList, searchCustomStepList));
    }

    private List<SearchResultElementResponseDto> getSearchCustomStepOfRoadmapStepList(Long userId,
                                                                                      String text,
                                                                                      SearchType searchType,
                                                                                      String subSearchType) {
        if (!(searchType == SearchType.ALL || searchType == SearchType.STEP)) return new ArrayList<>();
        List<SearchCustomStep> searchCustomStepList = getSearchCustomStep(userId, subSearchType, text);
        return getSearchResultElementListFromCustomStepList(searchCustomStepList);
    }

    private List<SearchResultElementResponseDto> getSearchResultOfCustomRoadmapList(Long userId,
                                                                                    String text,
                                                                                    SearchType searchType,
                                                                                    String subSearchType) {
        if (!(searchType == SearchType.ALL || searchType == SearchType.ROADMAP)) return new ArrayList<>();
        List<SearchCustomRoadmap> searchCustomRoadmapList = getSearchCustomRoadmap(userId, subSearchType, text);
        return getSearchResultElementListFromCustomRoadmapList(searchCustomRoadmapList);
    }

    private List<SearchResultElementResponseDto> getSearchResultOfCustomTemplateList(Long userId,
                                                                                     String text,
                                                                                     SearchType searchType,
                                                                                     String subSearchType) {
        if (!(searchType == SearchType.ALL || searchType == SearchType.TEMPLATE)) return new ArrayList<>();
        List<SearchCustomTemplate> searchCustomTemplateList = getSearchCustomTemplate(userId, subSearchType, text);
        return getSearchResultElementListFromCustomTemplateList(searchCustomTemplateList);
    }


    private List<SearchResultElementResponseDto> getSearchResultElementListFromCustomStepList(List<SearchCustomStep> searchCustomStepList) {
        return searchCustomStepList.stream()
                .map(searchStep ->
                        SearchResultElementResponseDto.of(
                                searchStep.getRoadmapId(),
                                SearchResultType.STEP,
                                searchStep.getTitle(),
                                searchStep.getRelatedRoadmapTitle(),
                                false,
                                searchStep.getRoadmapType().getStringRoadmapType()))
                .collect(Collectors.toList());
    }

    private List<SearchResultElementResponseDto> getSearchResultElementListFromCustomRoadmapList(List<SearchCustomRoadmap> searchCustomRoadmapList) {
        return searchCustomRoadmapList.stream()
                .map(searchCustomRoadmap ->
                        SearchResultElementResponseDto.of(
                                searchCustomRoadmap.getRoadmapId(),
                                SearchResultType.ROADMAP,
                                searchCustomRoadmap.getTitle(),
                                searchCustomRoadmap.getRelatedTeamTitle(),
                                false,
                                searchCustomRoadmap.getRoadmapType().getStringRoadmapType()))
                .collect(Collectors.toList());
    }

    private List<SearchResultElementResponseDto> getSearchResultElementListFromCustomTemplateList(List<SearchCustomTemplate> searchCustomTemplateList) {
        return searchCustomTemplateList.stream()
                .map(searchCustomTemplate ->
                        SearchResultElementResponseDto.of(
                                searchCustomTemplate.getTemplateId(),
                                SearchResultType.TEMPLATE,
                                searchCustomTemplate.getTitle(),
                                searchCustomTemplate.getRelatedTeamTitle(),
                                false,
                                searchCustomTemplate.getRelatedRoadmapTitle()))
                .collect(Collectors.toList());
    }

    private List<SearchCustomStep> getSearchCustomStep(Long userId,  String subSearchType, String text) {
        SubSearchType enumSubSearchType = getEnumSubSearchTypeFromString(subSearchType);
        if (enumSubSearchType == SubSearchType.ALL)
            return searchCustomStepRepository.findByUserIdAndTitleContainingIgnoreCase(userId, text);
        else {
            RoadmapType roadmapType = getEnumRoadmapTypeFromStringRoadmapType(subSearchType);
            return searchCustomStepRepository.findByUserIdAndRoadmapTypeAndTitleContainingIgnoreCase(userId, roadmapType, text);
        }
    }

    private List<SearchCustomRoadmap> getSearchCustomRoadmap(Long userId,  String subSearchType, String text) {
        SubSearchType enumSubSearchType = getEnumSubSearchTypeFromString(subSearchType);
        if (enumSubSearchType == SubSearchType.ALL)
            return searchCustomRoadmapRepository.findByUserIdAndTitleContainingIgnoreCase(userId, text);
        else {
            RoadmapType roadmapType = getEnumRoadmapTypeFromStringRoadmapType(subSearchType);
            return searchCustomRoadmapRepository.findByUserIdAndRoadmapTypeAndTitleContainingIgnoreCase(userId, roadmapType, text);
        }
    }

    private List<SearchCustomTemplate> getSearchCustomTemplate(Long userId,  String subSearchType, String text) {
        SubSearchType enumSubSearchType = getEnumSubSearchTypeFromString(subSearchType);
        if (enumSubSearchType == SubSearchType.ALL)
            return searchCustomTemplateRepository.findByUserIdAndTitleContainingIgnoreCase(userId, text);
        else {
            TemplateType templateType = getEnumTemplateTypeFromStringTemplateType(subSearchType);
            return searchCustomTemplateRepository.findByUserIdAndTemplateTypeAndTitleContainingIgnoreCase(userId, templateType, text);
        }
    }

    private List<SearchResultElementResponseDto> joinSearchResultList(List<SearchResultElementResponseDto> searchCustomTemplateList,
                                                                      List<SearchResultElementResponseDto> searchCustomRoadmpList,
                                                                      List<SearchResultElementResponseDto> searchCustomStepList) {
        return Stream.concat(searchCustomStepList.stream(), Stream.concat(searchCustomTemplateList.stream(),
                        searchCustomRoadmpList.stream())).collect(Collectors.toList());
    }
}
