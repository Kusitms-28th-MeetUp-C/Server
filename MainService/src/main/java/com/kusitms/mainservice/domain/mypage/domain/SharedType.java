package com.kusitms.mainservice.domain.mypage.domain;

import com.kusitms.mainservice.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.mainservice.global.error.ErrorCode.INVALID_SHARED_TYPE;

@RequiredArgsConstructor
@Getter
public enum SharedType {
    전체("전체"),
    템플릿("템플릿"),
    로드맵("로드맵");

    private final String stringSharedType;

    public static SharedType getEnumSharedTypeFromStringSharedType(String stringSharedType) {
        return Arrays.stream(values())
                .filter(sharedType -> sharedType.stringSharedType.equals(stringSharedType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_SHARED_TYPE));
    }
}
