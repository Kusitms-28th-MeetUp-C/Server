package com.kusitms.socketservice.domain.search.dto.response;

import com.kusitms.socketservice.domain.search.domain.SearchUserTemplate;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class SearchResultElementResponseDto {
    private Long templateId;
    private String title;
    private String teamTitle;
    private boolean isOpen;
    private String category;

    public static List<SearchResultElementResponseDto> listOf(List<SearchUserTemplate> searchUserTemplateList){
        return searchUserTemplateList.stream()
                .map(searchUserTemplate ->
                        SearchResultElementResponseDto.of(
                                searchUserTemplate.getTemplateId(),
                                searchUserTemplate.getTitle(),
                                searchUserTemplate.getRelatedTeamTitle(),
                                searchUserTemplate.isOpened(),
                                searchUserTemplate.getTemplateType().toString()
                        )).collect(Collectors.toList());
    }

    public static SearchResultElementResponseDto of(Long templateId, String title,
                                                    String teamTitle, boolean isOpen, String category){
        return SearchResultElementResponseDto.builder()
                .templateId(templateId)
                .title(title)
                .teamTitle(teamTitle)
                .isOpen(isOpen)
                .category(category)
                .build();
    }
}
