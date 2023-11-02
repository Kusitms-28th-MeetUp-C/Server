package com.kusitms.mainservice.domain.roadmap.controller;

import com.kusitms.mainservice.domain.roadmap.dto.response.UserRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.service.RoadmapService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/roadmap")
@RestController
public class RoadmapController {
    private final RoadmapService roadmapService;

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<?>> getUserRoadmapList(@UserId final Long userId){
        final UserRoadmapResponseDto responseDto = roadmapService.getUserRoadmapList(userId);
        return SuccessResponse.ok(responseDto);
    }
}
