package com.kusitms.mainservice.domain.mypage.controller;

import com.kusitms.mainservice.domain.mypage.dto.response.MyPageResponseDto;
import com.kusitms.mainservice.domain.mypage.dto.response.MyPageUserResponseDto;
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
    public ResponseEntity<SuccessResponse<?>> getMyPageResponse(@RequestParam Long userId, @PageableDefault(size=6) Pageable pageable){
        final MyPageResponseDto myPageResponseDto = myPageService.getMyPageResponse(userId,pageable);
        return SuccessResponse.ok(myPageResponseDto);
    }
    @PostMapping("/uploadProfile")
    public ResponseEntity<SuccessResponse<?>> uploadProfile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) {
        try {
            MyPageUserResponseDto myPageUserResponseDto=myPageService.uploadProfile(file, userId);
            return SuccessResponse.ok(myPageUserResponseDto);
        } catch (IOException e) {
            e.printStackTrace(); // 실제로는 로깅 등을 통해 에러를 처리해야 합니다.
            return SuccessResponse.ok("dd");
        }
    }
}
