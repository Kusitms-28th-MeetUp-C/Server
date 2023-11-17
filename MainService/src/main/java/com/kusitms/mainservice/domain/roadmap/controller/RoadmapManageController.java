package com.kusitms.mainservice.domain.roadmap.controller;

import com.kusitms.mainservice.domain.roadmap.dto.request.RoadmapSharingRequestDto;
import com.kusitms.mainservice.domain.roadmap.service.RoadmapManageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/manage/roadmap")
@RestController
public class RoadmapManageController {
    private final RoadmapManageService roadmapManageService;
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createRoadmap(@UserId Long userId, @RequestBody RoadmapSharingRequestDto roadmapSharingRequestDto){
        roadmapManageService.createSharingRoadmap(userId, roadmapSharingRequestDto);
        return SuccessResponse.ok("");
    }
}
