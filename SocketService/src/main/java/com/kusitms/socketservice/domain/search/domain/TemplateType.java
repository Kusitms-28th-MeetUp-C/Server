package com.kusitms.socketservice.domain.search.domain;

import com.kusitms.socketservice.global.error.httpException.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.socketservice.global.error.ErrorCode.INVALID_TEMPLATE_TYPE;

@RequiredArgsConstructor
@Getter
public enum TemplateType {
    IT("it"),
    SURVEY_DATA_ANALYSIS("survey_data_analysis"),
    CORPORATE_ANALYSIS("corporate_analysis"),
    PT("pt"),
    DESIGN("design"),
    MARKETING("marketing"),
    ETC("etc");

    private final String stringTemplateType;

    public static TemplateType getEnumTemplateTypeFromStringTemplateType(String stringTemplateType) {
        return Arrays.stream(values())
                .filter(templateType -> templateType.stringTemplateType.equals(stringTemplateType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_TEMPLATE_TYPE));
    }
}
