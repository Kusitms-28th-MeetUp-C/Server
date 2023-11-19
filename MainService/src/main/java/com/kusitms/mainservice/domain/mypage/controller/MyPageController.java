package com.kusitms.mainservice.domain.mypage.controller;

import com.kusitms.mainservice.domain.mypage.dto.response.*;
import com.kusitms.mainservice.domain.mypage.dto.resquest.ModifyUserProfileRequestDto;
import com.kusitms.mainservice.domain.mypage.service.MyPageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@RestController
public class MyPageController {
    private final MyPageService myPageService;
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getMyPageResponse(@UserId Long userId, @PageableDefault(size=6) Pageable pageable){
        final MyPageResponseDto myPageResponseDto = myPageService.getMyPageResponse(userId,pageable);
        return SuccessResponse.ok(myPageResponseDto);
    }
    @PostMapping("/shared")
    public ResponseEntity<SuccessResponse<?>> getSharedContent(@UserId Long userId, @RequestParam String sharedType, @PageableDefault(size=6) Pageable pageable){
        final Page<MySharedContentDto> mySharedContentDtoPage = myPageService.getSharedContentBySharedType(userId, sharedType,pageable);
        return SuccessResponse.ok(mySharedContentDtoPage);
    }
    @PostMapping("/uploadProfile")
    public ResponseEntity<SuccessResponse<?>> uploadProfile(@RequestPart("file") MultipartFile file,@UserId Long userId) throws IOException {
        final String url = myPageService.uploadProfile(file, userId);
        return SuccessResponse.ok(url);
    }


    @PostMapping("/update")
    public ResponseEntity<SuccessResponse<?>> updateUser(@UserId Long userId, @RequestBody ModifyUserProfileRequestDto modifyUserProfileRequestDto){
        final MyPageUserResponseDto myPageUserResponseDto = myPageService.updateUserInfo(userId,modifyUserProfileRequestDto);
        return SuccessResponse.ok(myPageUserResponseDto);
    }
    @GetMapping("/another/template")
    public ResponseEntity<SuccessResponse<?>> getAnotherUserTemplate(@RequestParam Long userId, @PageableDefault(size=12) Pageable pageable){
        final NotMyPageTemplateResponseDto notMyPageTemplateResponseDto = myPageService.getNotMyPageTemplateResponse(userId,pageable);
        return SuccessResponse.ok(notMyPageTemplateResponseDto);
    }
    @GetMapping("/another/roadmap")
    public ResponseEntity<SuccessResponse<?>> getAnotherUserRoadmap(@RequestParam Long userId, @PageableDefault(size=12) Pageable pageable){
        final NotMyPageRoadmapResponseDto notMyPageRoadmapResponseDto = myPageService.getNotMyPageRoadmapResponse(userId, pageable);
        return SuccessResponse.ok(notMyPageRoadmapResponseDto);
    }
}
