package com.kusitms.mainservice.domain.mypage.service;

import com.kusitms.mainservice.domain.mypage.domain.SharedType;
import com.kusitms.mainservice.domain.mypage.dto.response.MyPageResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.response.MyPageUserResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.response.MySharedContentDto;
import com.kusitms.mainservice.domain.mypage.dto.resquest.ModifyUserProfileRequestDto;
import com.kusitms.mainservice.domain.mypage.dto.resquest.MySharedContentRequestDto;
import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.S3Service;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kusitms.mainservice.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MyPageService {
    private final TemplateRepository templateRepository;
    private final RoadmapRepository roadmapRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public MyPageResponseDto getMyPageResponse(Long userId, Pageable pageable) {
        return createMyPageResponseDto(userId, pageable);
    }

    public Page<MySharedContentDto> getSharedContentBySharedType(MySharedContentRequestDto mySharedContentRequestDto, Pageable pageable) {
        return createMySharedContentDtoPage(mySharedContentRequestDto, pageable);
    }

    public String uploadProfile(MultipartFile multipartFile, Long userId) throws IOException {
        User user = getUserByUserId(userId);
        String url = saveFileToUser(multipartFile, user);
        return url;
    }

    public MyPageUserResponseDto updateUserInfo(ModifyUserProfileRequestDto modifyUserProfileRequestDto) {
        User user = getUserByUserId(modifyUserProfileRequestDto.getUserId());
        user.updateMypage(modifyUserProfileRequestDto);

        return createMyPageUserResponseDto(user);
    }

    private Page<MySharedContentDto> createMySharedContentDtoPage(MySharedContentRequestDto mySharedContentRequestDto, Pageable pageable) {

        if (mySharedContentRequestDto.getSharedType().equals("템플릿")) {
            Page<MySharedContentDto> mySharedContentDtoPage = createTemplateContentpage(mySharedContentRequestDto, pageable);
            return mySharedContentDtoPage;
        }
        if (mySharedContentRequestDto.getSharedType().equals("로드맵")) {
            Page<MySharedContentDto> mySharedContentDtoPage = createRoadmapContentpage(mySharedContentRequestDto, pageable);
            return mySharedContentDtoPage;
        }
        return createmySharedContentDtoList(mySharedContentRequestDto.getUserId(), pageable);
    }

    private Page<MySharedContentDto> createTemplateContentpage(MySharedContentRequestDto mySharedContentRequestDto, Pageable pageable) {
        Page<Template> templatePage = templateRepository.findAllByUserId(mySharedContentRequestDto.getUserId(), pageable);
        return templatePage.map(template ->
                MySharedContentDto.of(
                        template.getId(),
                        SharedType.템플릿,
                        template.getTitle(),
                        template.getTemplateType().toString()
                )
        );
    }

    private Page<MySharedContentDto> createRoadmapContentpage(MySharedContentRequestDto mySharedContentRequestDto, Pageable pageable) {
        Page<Roadmap> roadmapPage = roadmapRepository.findAllByUserId(mySharedContentRequestDto.getUserId(), pageable);
        return roadmapPage.map(roadmap ->
                MySharedContentDto.of(
                        roadmap.getId(),
                        SharedType.로드맵,
                        roadmap.getTitle(),
                        roadmap.getRoadmapType().toString()
                )
        );
    }

    private String saveFileToUser(MultipartFile multipartFile, User user) throws IOException {
        String url = s3Service.saveFile(multipartFile, user.getId().toString());
        user.updateProfile(url);
        return url;
    }

    private MyPageResponseDto createMyPageResponseDto(Long userId, Pageable pageable) {
        User user = getUserByUserId(userId);
        MyPageUserResponseDto myPageUserResponseDto = createMyPageUserResponseDto(user);
        Page<MySharedContentDto> mySharedContentDtoList = createmySharedContentDtoList(userId, pageable);
        return MyPageResponseDto.of(myPageUserResponseDto, mySharedContentDtoList);
    }

    private Page<MySharedContentDto> createmySharedContentDtoList(Long userId, Pageable pageable) {
        List<Template> templateList = templateRepository.findAllByUserId(userId);
        List<Roadmap> roadmapList = roadmapRepository.findAllByUserId(userId);
        List<MySharedContentDto> contentList = new ArrayList<>();

        // Template 리스트와 Roadmap 리스트를 합침
        for (Template template : templateList) {
            MySharedContentDto dto = MySharedContentDto.of(template.getId(), SharedType.템플릿, template.getTitle(), template.getTemplateType().toString());
            contentList.add(dto);
        }

        for (Roadmap roadmap : roadmapList) {
            MySharedContentDto dto = MySharedContentDto.of(roadmap.getId(), SharedType.로드맵, roadmap.getTitle(), roadmap.getRoadmapType().toString());
            contentList.add(dto);
        }

        // 페이지 번호와 페이지 크기를 이용하여 필요한 범위의 데이터 추출
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), contentList.size());

        // 추출한 데이터로 Page 객체 생성
        List<MySharedContentDto> pagedContentList = contentList.subList(start, end);

        int pageSize = pageable.getPageSize();

        Pageable adjustedPageable = PageRequest.of(pageable.getPageNumber(), pageSize);

        Page<MySharedContentDto> resultPage = new PageImpl<>(pagedContentList, adjustedPageable, contentList.size());

        return resultPage;
    }

    private MyPageUserResponseDto createMyPageUserResponseDto(User user) {

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
