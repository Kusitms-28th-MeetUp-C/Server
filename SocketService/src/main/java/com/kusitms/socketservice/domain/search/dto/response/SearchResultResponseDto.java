package com.kusitms.socketservice.domain.search.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchResultResponseDto {
    private List<SearchResultElementResponseDto> searchResult;

    public static SearchResultResponseDto of(List<SearchResultElementResponseDto> searchResult) {
        return SearchResultResponseDto.builder()
                .searchResult(searchResult)
                .build();
    }
}
