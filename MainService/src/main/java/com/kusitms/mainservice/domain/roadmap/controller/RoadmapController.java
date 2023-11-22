package com.kusitms.mainservice.domain.roadmap.controller;

import com.kusitms.mainservice.domain.roadmap.dto.request.SearchRoadmapRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapDetailInfoResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.SearchBaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.service.RoadmapService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/roadmap")
@RestController
public class RoadmapController {
    private final RoadmapService roadmapService;
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> getRoadmapByTitleAndRoadmapType(@RequestBody SearchRoadmapRequestDto searchRoadmapRequestDto, @PageableDefault(size = 12) Pageable pageable){
        final Page<SearchBaseRoadmapResponseDto> searchRoadmapResponseDto = roadmapService.searchRoadmapByTitleAndRoadmapType(searchRoadmapRequestDto, pageable);
        return SuccessResponse.ok(searchRoadmapResponseDto);
    }

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<?>> getRoadmapDetailByRoadmapId(@RequestParam Long roadmapId){
        final RoadmapDetailInfoResponseDto roadmapDetailInfoResponseDto = roadmapService.getRoadmapDetail(roadmapId);
        return SuccessResponse.ok(roadmapDetailInfoResponseDto);
    }

}
