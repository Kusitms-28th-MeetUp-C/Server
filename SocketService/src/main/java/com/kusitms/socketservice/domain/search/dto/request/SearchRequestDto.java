package com.kusitms.socketservice.domain.search.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchRequestDto {
    private String searchString;
    private String searchType;
    private String subSearchType;
}
