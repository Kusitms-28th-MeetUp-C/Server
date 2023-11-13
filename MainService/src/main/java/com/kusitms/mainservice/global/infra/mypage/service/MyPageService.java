package com.kusitms.mainservice.global.infra.mypage.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapDownloadRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.repository.TemplateDownloadRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import com.kusitms.mainservice.domain.user.service.UserService;
import com.kusitms.mainservice.global.infra.mypage.domain.SharedType;
import com.kusitms.mainservice.global.infra.mypage.dto.MyPageResponseDto;
import com.kusitms.mainservice.global.infra.mypage.dto.MyPageUserResponseDto;
import com.kusitms.mainservice.global.infra.mypage.dto.MySharedContentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MyPageService {
    private final TemplateDownloadRepository templateDownloadRepository;
    private final RoadmapDownloadRepository roadmapDownloadRepository;
    private final TemplateRepository templateRepository;
    private final RoadmapRepository roadmapRepository;
    private final UserService userService;
    public MyPageResponseDto getMyPageResponse(Long userId,Pageable pageable ){
        MyPageResponseDto myPageResponseDto = createMyPageResponseDto(userId, pageable);
    return myPageResponseDto;
    }
    private MyPageResponseDto createMyPageResponseDto(Long userId,Pageable pageable){
        MyPageUserResponseDto myPageUserResponseDto = createMyPageUserResponseDto(userId);
        Page<MySharedContentDto> mySharedContentDtoList = createmySharedContentDtoList(userId, pageable);
        return MyPageResponseDto.of(myPageUserResponseDto,mySharedContentDtoList);
    }
    private Page<MySharedContentDto> createmySharedContentDtoList(Long userId, Pageable pageable){
        Page<Template> templatePage = templateRepository.findAllByMaker_Id(userId, pageable);
        Page<Roadmap> roadmapPage = roadmapRepository.findAllByMaker_Id(userId,pageable);
        Page<MySharedContentDto> resultPage = Page.empty(pageable);

        for (Template template : templatePage.getContent()) {
            MySharedContentDto dto = MySharedContentDto.of(SharedType.getEnumSharedTypeFromStringSharedType("template"), template.getTitle(), template.getTemplateType().toString());
            resultPage.getContent().add(dto);
        }

        for (Roadmap roadmap : roadmapPage.getContent()) {
            MySharedContentDto dto = MySharedContentDto.of(SharedType.getEnumSharedTypeFromStringSharedType("roadmap"), roadmap.getTitle(), roadmap.getRoadmapType().toString());
            resultPage.getContent().add(dto);
        }

        resultPage = new PageImpl<>(resultPage.getContent(), pageable, resultPage.getTotalElements());

        return resultPage;
    }
    private MyPageUserResponseDto createMyPageUserResponseDto(Long userId){
        User user = userService.getUserByUserId(userId);
        DetailUserResponseDto detailUserResponseDto = userService.createDetailUserResponseDto(user);
        int templateNum = detailUserResponseDto.getTemplateNum();
        int roadmapNum = detailUserResponseDto.getRoadmapNum();
        int point = 0;
        return MyPageUserResponseDto.of(user,templateNum,roadmapNum,point);
    }
}
