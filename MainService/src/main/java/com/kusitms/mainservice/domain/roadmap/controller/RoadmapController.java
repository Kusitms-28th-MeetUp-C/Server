package com.kusitms.mainservice.domain.roadmap.controller;

import com.kusitms.mainservice.domain.roadmap.dto.response.BaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.service.RoadmapUserService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/roadmap")
@RestController
public class RoadmapController {
    private final RoadmapUserService roadmapUserService;

    @GetMapping("/team")
    public ResponseEntity<SuccessResponse<?>> getTeamRoadmap(@RequestParam final Long teamId){
        final BaseRoadmapResponseDto responseDto = roadmapUserService.getTeamRoadmap(teamId);
        return SuccessResponse.ok(responseDto);
    }
}
