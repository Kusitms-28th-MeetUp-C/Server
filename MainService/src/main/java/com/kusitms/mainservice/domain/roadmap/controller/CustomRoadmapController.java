package com.kusitms.mainservice.domain.roadmap.controller;

import com.kusitms.mainservice.domain.roadmap.service.CustomRoadmapService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/custom/roadmap")
@RestController
public class CustomRoadmapController {
    private final CustomRoadmapService customRoadmapService;

    @PatchMapping("/space")
    public ResponseEntity<SuccessResponse<?>> updateCompletedRoadmapSpace(@RequestParam final Long stepId,
                                                                          @RequestParam final boolean isCompleted) {
        customRoadmapService.updateCompletedRoadmapSpace(stepId, isCompleted);
        return SuccessResponse.ok(null);
    }
}
