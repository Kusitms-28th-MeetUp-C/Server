package com.kusitms.socketservice.domain.search.domain;

import com.kusitms.socketservice.global.error.socketException.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.socketservice.global.error.ErrorCode.SUB_SEARCH_TYPE_NOT_FOUND;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SearchResultType {
    TEMPLATE("template"),
    ROADMAP("roadmap"),
    STEP("step");

    private final String SearchResultType;

    public static SearchResultType getEnumSearchResultTypeFromString(String stringSearchResultType) {
        return Arrays.stream(values())
                .filter(searchResultType -> searchResultType.SearchResultType.equals(stringSearchResultType))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(SUB_SEARCH_TYPE_NOT_FOUND));
    }
}
