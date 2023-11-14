package com.kusitms.mainservice.domain.mypage.service;

import com.kusitms.mainservice.domain.mypage.domain.SharedType;
import com.kusitms.mainservice.domain.mypage.dto.MyPageResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.MyPageUserResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.MySharedContentDto;
import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapDownloadRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.repository.TemplateDownloadRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.kusitms.mainservice.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MyPageService {
    private final TemplateDownloadRepository templateDownloadRepository;
    private final RoadmapDownloadRepository roadmapDownloadRepository;
    private final TemplateRepository templateRepository;
    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;

    public MyPageResponseDto getMyPageResponse(Long userId, Pageable pageable) {
        MyPageResponseDto myPageResponseDto = createMyPageResponseDto(userId, pageable);
        return myPageResponseDto;
    }

    private MyPageResponseDto createMyPageResponseDto(Long userId, Pageable pageable) {
        MyPageUserResponseDto myPageUserResponseDto = createMyPageUserResponseDto(userId);
        Page<MySharedContentDto> mySharedContentDtoList = createmySharedContentDtoList(userId, pageable);
        return MyPageResponseDto.of(myPageUserResponseDto, mySharedContentDtoList);
    }

    private Page<MySharedContentDto> createmySharedContentDtoList(Long userId, Pageable pageable) {
        List<Template> templateList = templateRepository.findAllByUserId(userId);
        List<Roadmap> roadmapList = roadmapRepository.findAllByUserId(userId);
        List<MySharedContentDto> contentList = new ArrayList<>();

        // Template 리스트와 Roadmap 리스트를 합침
        for (Template template : templateList) {
            MySharedContentDto dto = MySharedContentDto.of(template.getId(), SharedType.Template, template.getTitle(), template.getTemplateType().toString());
            contentList.add(dto);
        }

        for (Roadmap roadmap : roadmapList) {
            MySharedContentDto dto = MySharedContentDto.of(roadmap.getId(), SharedType.Roadmap, roadmap.getTitle(), roadmap.getRoadmapType().toString());
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

    private MyPageUserResponseDto createMyPageUserResponseDto(Long userId) {
        User user = getUserByUserId(userId);
        DetailUserResponseDto detailUserResponseDto = createDetailUserResponseDto(user);
        int templateNum = detailUserResponseDto.getTemplateNum();
        int roadmapNum = detailUserResponseDto.getRoadmapNum();
        int point = 0;
        return MyPageUserResponseDto.of(user, templateNum, roadmapNum, point);
    }

    private User getUserByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public DetailUserResponseDto createDetailUserResponseDto(User user) {
        int templateNum = getTemplateCountByUser(user);
        int roadmapNum = getRoadmapCountByUser(user);
        return DetailUserResponseDto.of(user, templateNum, roadmapNum);
    }

    private int getTemplateCountByUser(User user) {
        return templateRepository.countByUser(user);
    }

    private int getRoadmapCountByUser(User user) {
        return roadmapRepository.countByUser(user);
    }
}
