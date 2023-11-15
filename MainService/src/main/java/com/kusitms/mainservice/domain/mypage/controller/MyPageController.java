package com.kusitms.mainservice.domain.mypage.controller;

import com.kusitms.mainservice.domain.mypage.dto.response.MyPageResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.response.MyPageUserResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.response.MySharedContentDto;
import com.kusitms.mainservice.domain.mypage.dto.resquest.ModifyUserProfileRequestDto;
import com.kusitms.mainservice.domain.mypage.dto.resquest.MySharedContentRequestDto;
import com.kusitms.mainservice.domain.mypage.service.MyPageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SuccessResponse<?>> uploadProfile(@RequestPart("file") MultipartFile file,@RequestParam Long userId) throws IOException {

        final String url = myPageService.uploadProfile(file, userId);
        return SuccessResponse.ok(url);
    }


    @PostMapping("/update")
    public ResponseEntity<SuccessResponse<?>> updateUser(@RequestBody ModifyUserProfileRequestDto modifyUserProfileRequestDto){
        final MyPageUserResponseDto myPageUserResponseDto = myPageService.updateUserInfo(modifyUserProfileRequestDto);
        return SuccessResponse.ok(myPageUserResponseDto);
    }
}
