package com.kusitms.socketservice.domain.search.domain;

import com.kusitms.socketservice.global.error.socketException.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.socketservice.global.error.ErrorCode.SEARCH_TYPE_NOT_FOUND;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SearchType {
    ALL("all"),
    ROADMAP("roadmap"),
    STEP("step"),
    TEMPLATE("template");

    private final String stringSearchType;

    public static SearchType getEnumSearchTypeFromString(String stringSearchType) {
        return Arrays.stream(values())
                .filter(searchType -> searchType.stringSearchType.equals(stringSearchType))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(SEARCH_TYPE_NOT_FOUND));
    }
}
