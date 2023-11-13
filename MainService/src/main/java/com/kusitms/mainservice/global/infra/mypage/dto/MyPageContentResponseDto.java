package com.kusitms.mainservice.global.infra.mypage.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MyPageContentResponseDto {
    List<MySharedContentDto> mySharedContentDtoList;
}
