package com.kusitms.mainservice.global.infra.mypage.domain;

import com.kusitms.mainservice.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.mainservice.global.error.ErrorCode.INVALID_TEMPLATE_TYPE;

@RequiredArgsConstructor
@Getter
public enum SharedType {
    ALL("all"),
    Template("template"),
    Roadmap("roadmap");

    private final String stringSharedType;

    public static SharedType getEnumSharedTypeFromStringSharedType(String stringSharedType) {
        return Arrays.stream(values())
                .filter(sharedType -> sharedType.stringSharedType.equals(stringSharedType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TEMPLATE_TYPE));
    }
}
