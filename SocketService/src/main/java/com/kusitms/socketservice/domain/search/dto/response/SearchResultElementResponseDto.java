package com.kusitms.socketservice.domain.search.dto.response;

import com.kusitms.socketservice.domain.search.domain.SearchResultType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchResultElementResponseDto {
    private Long id;
    private SearchResultType resultType;
    private String title;
    private String teamTitle;
    private boolean isOpen;
    private String category;

    public static SearchResultElementResponseDto of(Long id, SearchResultType resultType, String title,
                                                    String teamTitle, boolean isOpen, String category){
        return SearchResultElementResponseDto.builder()
                .id(id)
                .resultType(resultType)
                .title(title)
                .teamTitle(teamTitle)
                .isOpen(isOpen)
                .category(category)
                .build();
    }
}
