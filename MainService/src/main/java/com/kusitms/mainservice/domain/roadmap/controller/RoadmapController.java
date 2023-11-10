package com.kusitms.mainservice.domain.roadmap.controller;

import com.kusitms.mainservice.domain.roadmap.dto.request.SearchRoadmapRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.BaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.SearchRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.service.CustomRoadmapService;
import com.kusitms.mainservice.domain.roadmap.service.RoadmapService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/roadmap")
@RestController
public class RoadmapController {
    private final RoadmapService roadmapService;

    @PostMapping("/get")
    public ResponseEntity<SuccessResponse<?>> getRoadmapByTitleAndRoadmaptype(@RequestBody SearchRoadmapRequestDto searchRoadmapRequestDto){
        final SearchRoadmapResponseDto searchRoadmapResponseDto = roadmapService.searchRoadmapByTitleAndRoadmapType(searchRoadmapRequestDto);
        return SuccessResponse.ok(searchRoadmapResponseDto);
    }

}
