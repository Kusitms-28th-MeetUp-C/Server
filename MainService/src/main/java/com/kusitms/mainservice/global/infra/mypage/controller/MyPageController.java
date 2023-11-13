package com.kusitms.mainservice.global.infra.mypage.controller;

import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.infra.mypage.dto.MyPageResponseDto;
import com.kusitms.mainservice.global.infra.mypage.service.MyPageService;
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
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getMyPageResponse(@RequestParam Long userId, @PageableDefault Pageable pageable){
        final MyPageResponseDto myPageResponseDto = myPageService.getMyPageResponse(userId,pageable);
        return SuccessResponse.ok(myPageResponseDto);
    }
}
