package com.kusitms.mainservice.global.infra.mypage.service;

import com.kusitms.mainservice.domain.roadmap.repository.RoadmapDownloadRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateDownloadRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.domain.user.service.UserService;
import com.kusitms.mainservice.global.config.auth.UserId;
import com.kusitms.mainservice.global.infra.mypage.dto.MyPageContentResponseDto;
import com.kusitms.mainservice.global.infra.mypage.dto.MyPageResponseDto;
import com.kusitms.mainservice.global.infra.mypage.dto.MyPageUserResponseDto;
import com.kusitms.mainservice.global.infra.mypage.dto.MySharedContentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MyPageService {
    private final TemplateDownloadRepository templateDownloadRepository;
    private final RoadmapDownloadRepository roadmapDownloadRepository;
    private final UserService userService;
    public MyPageResponseDto getMyPageResponse(){
        MyPageResponseDto myPageResponseDto = createMyPageResponseDto();
    }
    private MyPageResponseDto createMyPageResponseDto(Long userId){
        MyPageUserResponseDto myPageUserResponseDto = createMyPageUserResponseDto(userId);
        List<MySharedContentDto> mySharedContentDtoList = createmySharedContentDtoList();
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
