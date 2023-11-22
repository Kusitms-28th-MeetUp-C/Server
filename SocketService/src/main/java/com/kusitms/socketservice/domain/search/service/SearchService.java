package com.kusitms.socketservice.domain.search.service;

import com.kusitms.socketservice.domain.search.domain.SearchUserTemplate;
import com.kusitms.socketservice.domain.search.dto.request.SearchRequestDto;
import com.kusitms.socketservice.domain.search.dto.response.SearchResultElementResponseDto;
import com.kusitms.socketservice.domain.search.dto.response.SearchResultResponseDto;
import com.kusitms.socketservice.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final SearchRepository searchRepository;
    private final static String EMPTY_STRING = "";

    public SearchResultResponseDto getSearchResult(Long userId, SearchRequestDto searchRequestDto) {
        List<SearchUserTemplate> searchUserTemplateList
                = getSearchResult(searchRequestDto.getSearchText(), userId.toString());
        List<SearchResultElementResponseDto> searchResultElementResponseDtoList
                = SearchResultElementResponseDto.listOf(searchUserTemplateList);
        return SearchResultResponseDto.of(searchResultElementResponseDtoList);
    }
    public void update(Long templateId, String relatedTeamTitle){
        searchRepository.updateRelatedTeamTitle(templateId, relatedTeamTitle);
    }

    private List<SearchUserTemplate> getSearchResult(String searchText, String userId){
        if(!Objects.equals(searchText, EMPTY_STRING))
            return searchRepository.findAllBySearchText(searchText, userId);
        else
            return searchRepository.findAllByUserId(userId);
    }

}
