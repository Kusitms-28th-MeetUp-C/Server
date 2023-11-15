package com.kusitms.socketservice.domain.search.service;

import com.kusitms.socketservice.domain.search.domain.SearchUserTemplate;
import com.kusitms.socketservice.domain.search.dto.request.SearchRequestDto;
import com.kusitms.socketservice.domain.search.dto.response.SearchResultElementResponseDto;
import com.kusitms.socketservice.domain.search.dto.response.SearchResultResponseDto;
import com.kusitms.socketservice.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final SearchRepository searchRepository;

    public SearchResultResponseDto getSearchResult(Long userId, SearchRequestDto searchRequestDto) {
        List<SearchUserTemplate> searchUserTemplateList
                = searchRepository.findAllBySearchText(searchRequestDto.getSearchText(), userId.toString());
        List<SearchResultElementResponseDto> searchResultElementResponseDtoList
                = SearchResultElementResponseDto.listOf(searchUserTemplateList);
        return SearchResultResponseDto.of(searchResultElementResponseDtoList);
    }
}
