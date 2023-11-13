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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.kusitms.mainservice.global.infra.mypage.domain.SharedType.getEnumSharedTypeFromStringSharedType;

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
    private Page<MySharedContentDto> createmySharedContentDtoList(Long userId, Pageable pageable) {
        List<Template> templateList = templateRepository.findAllByUserId(userId);
        List<Roadmap> roadmapList = roadmapRepository.findAllByUserId(userId);
        List<MySharedContentDto> contentList = new ArrayList<>();

        // Template 리스트와 Roadmap 리스트를 합침
        for (Template template : templateList) {
            MySharedContentDto dto = MySharedContentDto.of(template.getId(),SharedType.Template, template.getTitle(), template.getTemplateType().toString());
            contentList.add(dto);
        }

        for (Roadmap roadmap : roadmapList) {
            MySharedContentDto dto = MySharedContentDto.of(roadmap.getId(),SharedType.Roadmap, roadmap.getTitle(), roadmap.getRoadmapType().toString());
            contentList.add(dto);
        }

        // 페이지 번호와 페이지 크기를 이용하여 필요한 범위의 데이터 추출
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), contentList.size());

        // 추출한 데이터로 Page 객체 생성
        List<MySharedContentDto> pagedContentList = contentList.subList(start, end);

        // pagesize를 2배로 설정
        int pageSize = pageable.getPageSize() * 2;

        Pageable adjustedPageable = PageRequest.of(pageable.getPageNumber(), pageSize);

        Page<MySharedContentDto> resultPage = new PageImpl<>(pagedContentList, adjustedPageable, contentList.size());

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
