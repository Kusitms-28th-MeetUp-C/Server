package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.mainservice.global.error.ErrorCode.INVALID_TEMPLATE_TYPE;

@RequiredArgsConstructor
@Getter
public enum TemplateType {
    ALL("all"),
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