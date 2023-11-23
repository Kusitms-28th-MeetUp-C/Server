package com.kusitms.mainservice.domain.mypage.dto.response;

import com.kusitms.mainservice.domain.template.dto.response.SearchBaseTemplateResponseDto;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class NotMyPageTemplateResponseDto {
    private DetailUserResponseDto user;
    private Page<SearchBaseTemplateResponseDto> templateList;

    public static NotMyPageTemplateResponseDto of(DetailUserResponseDto user, Page<SearchBaseTemplateResponseDto> templateList) {
        return NotMyPageTemplateResponseDto.builder()
                .user(user)
                .templateList(templateList)
                .build();
    }
}
