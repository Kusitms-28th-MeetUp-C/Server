package com.kusitms.mainservice.domain.team.controller;

import com.kusitms.mainservice.domain.team.dto.request.TeamRequestDto;
import com.kusitms.mainservice.domain.team.dto.request.TeamRoadmapRequestDto;
import com.kusitms.mainservice.domain.team.dto.request.UpdateTeamRequestDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamListResponseDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamResponseDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamRoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.team.service.TeamRoadmapService;
import com.kusitms.mainservice.domain.team.service.TeamService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/team")
@RestController
public class TeamController {
    private final TeamService teamService;
    private final TeamRoadmapService teamRoadmapService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getAllTeamList(@UserId final Long userId){
        final TeamListResponseDto responseDto = teamService.getAllTeamList(userId);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createTeam(@UserId final Long userId,
                                                         @RequestBody final TeamRequestDto requestDto) {
        final TeamResponseDto responseDto = teamService.createTeam(userId, requestDto);
        return SuccessResponse.created(responseDto);
    }

    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> updateTeamInfo(@RequestBody final UpdateTeamRequestDto requestDto) {
        final TeamResponseDto responseDto = teamService.updateTeamInfo(requestDto);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/roadmap")
    public ResponseEntity<SuccessResponse<?>> addTeamRoadmap(@UserId final Long userId,
                                                             @RequestBody final TeamRoadmapRequestDto requestDto) {
        teamRoadmapService.addTeamRoadmap(userId, requestDto);
        return SuccessResponse.created(null);
    }

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<?>> getTeamRoadmapDetail(@RequestParam final Long teamId) {
        final TeamRoadmapDetailResponseDto responseDto = teamRoadmapService.getTeamRoadmapDetail(teamId);
        return SuccessResponse.ok(responseDto);
    }
}
