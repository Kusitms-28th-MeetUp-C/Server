package com.kusitms.mainservice.domain.roadmap.controller;

import com.kusitms.mainservice.domain.roadmap.dto.request.RoadmapSharingRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapStepDto;
import com.kusitms.mainservice.domain.roadmap.service.RoadmapManageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/manage/roadmap")
@RestController
public class RoadmapManageController {
    private final RoadmapManageService roadmapManageService;
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createRoadmap(@UserId Long userId, @RequestBody RoadmapSharingRequestDto roadmapSharingRequestDto){
        roadmapManageService.createSharingRoadmap(userId, roadmapSharingRequestDto);
        return SuccessResponse.created(null);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getCustomSpaceStep(@UserId Long userId, @RequestParam String title){
        final List<CustomRoadmapStepDto> customRoadmapStepDtoList = roadmapManageService.getCustomRoadmapStepDto(userId, title);
        return SuccessResponse.ok(customRoadmapStepDtoList);

    }
}
