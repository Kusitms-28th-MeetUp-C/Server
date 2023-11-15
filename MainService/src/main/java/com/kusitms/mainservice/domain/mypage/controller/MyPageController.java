package com.kusitms.mainservice.domain.mypage.controller;

import com.kusitms.mainservice.domain.mypage.dto.response.MyPageResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.response.MyPageUserResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.resquest.ModifyUserProfileRequestDto;
import com.kusitms.mainservice.domain.mypage.service.MyPageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/get")
    public ResponseEntity<SuccessResponse<?>> getMyPageResponse(@RequestBody Long userId, @PageableDefault(size=6) Pageable pageable){
        final MyPageResponseDto myPageResponseDto = myPageService.getMyPageResponse(userId,pageable);
        return SuccessResponse.ok(myPageResponseDto);
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
