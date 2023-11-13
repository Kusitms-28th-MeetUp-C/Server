package com.kusitms.mainservice.domain.template.dto.response;

import com.kusitms.mainservice.domain.template.domain.Template;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class TemplateDetailIntroResponseDto {
    private String date;
    private TemplateDetailIntroBaseResponseDto simpleInfo;
    private String introduction;
    private List<ReviewContentResponseDto> reviews;

    public static TemplateDetailIntroResponseDto of(Template template, TemplateDetailIntroBaseResponseDto templateIntro, List<ReviewContentResponseDto> reviews){
        return TemplateDetailIntroResponseDto.builder()
                .date(template.getDate())
                .simpleInfo(templateIntro)
                .introduction(template.getIntroduction())
                .reviews(reviews)
                .build();
    }
}
