package com.kusitms.mainservice.domain.team.controller;

import com.kusitms.mainservice.domain.team.dto.request.TeamRequestDto;
import com.kusitms.mainservice.domain.team.dto.request.TeamRoadmapRequestDto;
import com.kusitms.mainservice.domain.team.dto.request.UpdateTeamRequestDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamResponseDto;
import com.kusitms.mainservice.domain.team.service.TeamService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/team")
@RestController
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createTeam(@RequestHeader final Long userId,
                                                         @RequestBody final TeamRequestDto requestDto) {
        final TeamResponseDto responseDto = teamService.createTeam(userId, requestDto);
        return SuccessResponse.ok(responseDto);
    }

    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> updateTeamInfo(@RequestBody final UpdateTeamRequestDto requestDto) {
        final TeamResponseDto responseDto = teamService.updateTeamInfo(requestDto);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/roadmap")
    public ResponseEntity<SuccessResponse<?>> addTeamRoadmap(@RequestHeader final Long userId,
                                                             @RequestBody final TeamRoadmapRequestDto requestDto) {
        teamService.addTeamRoadmap(userId, requestDto);
        return SuccessResponse.ok(null);
    }
}
