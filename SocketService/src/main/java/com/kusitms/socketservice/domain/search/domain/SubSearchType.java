package com.kusitms.socketservice.domain.search.domain;

import com.kusitms.socketservice.global.error.socketException.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.socketservice.global.error.ErrorCode.SUB_SEARCH_TYPE_NOT_FOUND;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SubSearchType {
    ALL("all"),
    IT("it"),
    SURVEY_DATA_ANALYSIS("survey_data_analysis"),
    CORPORATE_ANALYSIS("corporate_analysis"),
    PT("pt"),
    DESIGN("design"),
    MARKETING("marketing");

    private final String stringSubSearchType;

    public static SubSearchType getEnumSubSearchTypeFromString(String stringSubSearchType) {
        return Arrays.stream(values())
                .filter(subSearchType -> subSearchType.stringSubSearchType.equals(stringSubSearchType))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(SUB_SEARCH_TYPE_NOT_FOUND));
    }
}
