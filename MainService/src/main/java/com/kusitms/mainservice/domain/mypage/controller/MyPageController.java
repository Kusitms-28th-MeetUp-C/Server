package com.kusitms.mainservice.domain.mypage.controller;

import com.kusitms.mainservice.domain.mypage.dto.response.MyPageResponseDto;
import com.kusitms.mainservice.domain.mypage.service.MyPageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
